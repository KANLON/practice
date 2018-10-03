package com.kanlon.table;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 比较两个表格的测试类
 *
 * @author zhangcanlong
 * @date 2018年10月3日
 */
public class CompareTable {
	public static void main(String[] args) {
		JExcelOption option = new JExcelOption();
		Properties prop = new Properties();
		try {
			prop.load(new InputStreamReader(new FileInputStream(".\\table_info.properties"), "UTF-8"));
		} catch (IOException e) {
			throw new RuntimeException(
					"读取配置文件错误！请确认本目录下info.properties文件存在\n" + CustomExceptionTool.getExceptionMsg(e));
		}
		String excelPath1 = prop.getProperty("excel_path1");
		String excelPath2 = prop.getProperty("excel_path2");
		String compareResultPath = prop.getProperty("compare_result_path");
		try {
			boolean flag = option.compareTwoTable(excelPath1, excelPath2, compareResultPath);
			if (flag) {
				System.out.println("表1和表2是一样数据，不存在不同");
			} else {
				System.out.println("表1和表2的数据存在不同，比较结果已经输出到：" + compareResultPath);
			}
		} catch (Exception e) {
			throw new RuntimeException("比较表1和表2时存在错误！请确认关闭所有相应excel表后重试！\n" + CustomExceptionTool.getExceptionMsg(e));
		}
	}

}
