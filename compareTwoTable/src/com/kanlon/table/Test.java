package com.kanlon.table;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class Test {

	public static void main(String[] args) {
		JExcelOption option = new JExcelOption();
		Properties prop = new Properties();
		try {
			prop.load(new InputStreamReader(new FileInputStream(".\\info.properties"), "UTF-8"));
		} catch (IOException e) {
			throw new RuntimeException(
					"读取配置文件错误！请确认本目录下info.properties文件存在\n" + CustomExceptionTool.getExceptionMsg(e));
		}
		String paperPath = prop.getProperty("target_path");
		String elecPath = prop.getProperty("excel_path");
		try {
			boolean flag = option.compareAwardTable(paperPath, elecPath);
			if (flag) {
				System.out.println("电子版和纸质版一样数据，不存在不同");
			} else {
				System.out.println("电子版信息和纸质版信息存在不同，新的比较结果已经输出到：" + paperPath);
			}
		} catch (Exception e) {
			throw new RuntimeException(
					"纸质版信息表格时和电子版信息表格是发送错误！请确认关闭所有相应excel表后重试！\n" + CustomExceptionTool.getExceptionMsg(e));
		}
	}

}
