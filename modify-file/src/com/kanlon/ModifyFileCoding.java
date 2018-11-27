package com.kanlon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 批量修改文件的编码
 *
 * @author zhangcanlong
 * @date 2018年11月27日
 */
public class ModifyFileCoding {

	/**
	 * 将gbk编码转化为utf-8编码（由于我本地项目为utf-8默认是utf-8）
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		String path = "D:\\yonyou\\interviewOffer\\src\\com\\kanlon";
		List<File> files = traverseFolderWithFor(path);
		if (!files.isEmpty() || files.size() >= 1) {
			for (File f : files) {
				writerStrToFile(readString(f.getAbsolutePath()), f.getAbsolutePath());
				System.out.println("更改了" + f.getAbsolutePath() + "的编码，它目前的编码为：UTF-8");
			}
		}
	}

	/**
	 * 非递归遍历某目录下所有的文件
	 *
	 * @param path
	 * @return
	 */
	public static List<File> traverseFolderWithFor(String path) {
		List<File> fileList = new ArrayList<>();
		File file = new File(path);

		if (file.exists()) {
			// 如果是当前目录是文件，直接返回该文件
			if (file.isFile()) {
				fileList.add(file);
				return fileList;
			}

			LinkedList<File> dirList = new LinkedList<>();
			File[] files = file.listFiles();
			for (File f : files) {
				// 如果是目录，加入目录集合中
				if (f.isDirectory()) {
					dirList.add(f);
				} else {
					fileList.add(f);
				}
			}
			// 临时文件，存储集合中目录
			File tempFile;
			while (!dirList.isEmpty()) {
				tempFile = dirList.removeFirst();
				files = tempFile.listFiles();
				for (File f : files) {
					if (f.isDirectory()) {
						dirList.add(f);
					} else {
						fileList.add(f);
					}
				}
			}
		} else {
			throw new RuntimeException("文件不存在");
		}
		return fileList;
	}

	/**
	 * 以循环方式遍历某目录下的所有文件
	 *
	 * @param path
	 * @param fileList
	 */
	public static void traverseFolderWithLoop(String path, List<File> fileList) {

		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (null == files || files.length == 0) {
				return;
			} else {
				for (File file2 : files) {
					if (file2.isDirectory()) {
						traverseFolderWithLoop(file2.getAbsolutePath(), fileList);
					} else {
						fileList.add(file2);
					}
				}
			}
		} else {
			throw new RuntimeException("文件不存在");
		}
	}

	/**
	 * 将文件内容读取出来转化为字符串（指定以gbk读取）
	 *
	 * @param filePath
	 *            文件路径
	 * @return 返回文件的内容
	 */
	public static String readString(String filePath) {
		int len = 0;
		StringBuffer str = new StringBuffer("");
		File file = new File(filePath);
		try {
			FileInputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is, "GBK");
			BufferedReader in = new BufferedReader(isr);
			String line = null;
			while ((line = in.readLine()) != null) {
				if (len != 0) // 处理换行符的问题
				{
					str.append(System.lineSeparator() + line);
				} else {
					str.append(line);
				}
				len++;
			}
			in.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str.toString();
	}

	/**
	 * 将文本写入到文件中去
	 *
	 * @param str
	 * @param filePath
	 */
	public static void writerStrToFile(String str, String filePath) {
		File file = new File(filePath);
		file.delete();
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter writer = new FileWriter(file);
			writer.write(str);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
