package com.kanlon;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * 自定义的异常的一些操作，主要是获取异常的详细信息
 *
 * @author zhangcanlong
 * @date 2018年11月7日
 */
public class CustomerExceptionTool {

	/**
	 * 得到详细的异常信息
	 *
	 * @param e
	 *            异常
	 * @return 返回异常的详细信息的字符串
	 */
	public static String getException(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		e.printStackTrace(pw);
		pw.flush();
		sw.flush();
		return new Date().toString() + sw.toString();
	}
}
