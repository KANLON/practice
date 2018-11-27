package com.kanlon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 其他网上的代码 https://www.cnblogs.com/gaopeng527/p/5155827.html
 *
 * @author zhangcanlong
 * @date 2018年11月27日
 */
public class OtherPersonCode {
	public static int fileCount = 0;
	public static String sourceFileRoot = "D:/workspace/test/src/com/zuidaima/"; // 将要转换文件所在的根目录
	public static String sourceCharset = "gbk"; // 源文件编码
	public static String targetCharset = "utf8"; // 目标文件编码

	public static void main(String[] args) throws IOException {
		File fileDir = new File(sourceFileRoot);
		convert(fileDir);
		System.out.println("Total Dealed : " + fileCount + "Files");
	}

	public static void convert(File file) throws IOException {
		// 如果是文件则进行编码转换，写入覆盖原文件
		if (file.isFile()) {
			// 只处理.java结尾的代码文件
			if (file.getPath().indexOf(".java") == -1) {
				return;
			}
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file), sourceCharset);
			BufferedReader br = new BufferedReader(isr);
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				// 注意写入换行符
				sb.append(line + "\n");
			}
			br.close();
			isr.close();

			File targetFile = new File(file.getPath() + "." + targetCharset);
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(targetFile), targetCharset);
			BufferedWriter bw = new BufferedWriter(osw);
			// 以字符串的形式一次性写入
			bw.write(sb.toString());
			bw.close();
			osw.close();

			System.out.println("Deal:" + file.getPath());
			fileCount++;
		} else {
			for (File subFile : file.listFiles()) {
				convert(subFile);
			}
		}
	}

}