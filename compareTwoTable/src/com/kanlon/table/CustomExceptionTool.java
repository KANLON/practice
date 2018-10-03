package com.kanlon.table;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * 自定义的异常一些操作
 *
 * @author zhangcanlong
 * @date 2018年10月1日
 */
public class CustomExceptionTool {
	/**
	 * 得到详细的异常信息
	 *
	 * @param e
	 * @return
	 */
	public static String getExceptionMsg(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		e.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return new Date().toString() + sw.toString();
	}
}
