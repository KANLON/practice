package com.kanlon;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SharesDateServlet extends HttpServlet {

	private static final long serialVersionUID = 7142804512583167572L;
	// ��Ÿ�����Ʊ�����
	private Map<String, BigInteger> map = new HashMap<>();
	// ��֤��Ʊָ��
	String shangzhengURL = "http://hq.sinajs.cn/list=s_sh000001";
	// �����֤��Ʊָ����txt�ļ�
	String shangzhengFilePath = Constant.WEB_APP_ROOT + "/shangzheng.txt";
	// ��������
	String tuoriURL = "http://hq.sinajs.cn/list=sz002218";
	String tuoriFilePath = Constant.WEB_APP_ROOT + "tuori.txt";
	// ������·
	String daqingURL = "http://hq.sinajs.cn/list=sh601006";
	String daqingFilePath = Constant.WEB_APP_ROOT + "daqing.txt";

	@Override
	public void init() throws ServletException {
		// ��ָ֤�������
		map.put(shangzhengURL, new BigInteger("0"));
		// ��������
		map.put(tuoriURL, new BigInteger("0"));
		new Thread() {
			@Override
			public void run() {
				SharesData.getShangzhengSharesData(shangzhengURL, shangzhengFilePath, map);
			}
		}.start();
		new Thread() {
			@Override
			public void run() {
				SharesData.getOtherSharesData(tuoriURL, tuoriFilePath, map);
			}
		}.start();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("��Ŀ��Ŀ¼1" + System.getProperty("root").replace("\\", "/"));
		System.out.println("��Ŀ��Ŀ¼1" + getServletContext().getRealPath("/"));

		resp.setContentType("text/html;charset=utf-8");
		// �����߼�ʵ��
		PrintWriter out = resp.getWriter();

		out.println("��ָ֤��&nbsp;&nbsp;&nbsp;&nbsp;��������<br/>");
		out.flush();
		while (true) {
			StringBuffer htmlMsg = new StringBuffer();
			htmlMsg.append(map.get(shangzhengURL) + "&nbsp;&nbsp;&nbsp;&nbsp;" + map.get(tuoriURL) + "<br/>");
			out.println(htmlMsg.toString());
			out.flush();
			// ������ǽ���ʱ�����ж����
			if (!SharesData.isTradingTime()) {
				out.println("���ڲ��ǲ�����ʱ�䣡����<br/>");
				out.flush();
				out.close();
				break;
			}
			try {
				Thread.sleep(1000 * 10);
			} catch (InterruptedException e) {
				SharesData.logger.log(Level.WARNING, "�����html���̴߳��󣡣�" + CustomerExceptionTool.getException(e));
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}
