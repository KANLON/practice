package com.kanlon;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * �Զ�����쳣��һЩ��������Ҫ�ǻ�ȡ�쳣����ϸ��Ϣ
 *
 * @author zhangcanlong
 * @date 2018��11��7��
 */
public class CustomerExceptionTool {

	/**
	 * �õ���ϸ���쳣��Ϣ
	 *
	 * @param e
	 *            �쳣
	 * @return �����쳣����ϸ��Ϣ���ַ���
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
