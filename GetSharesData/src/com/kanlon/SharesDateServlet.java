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
	// 存放各个股票的序号
	private Map<String, BigInteger> map = new HashMap<>();
	// 上证股票指数
	String shangzhengURL = "http://hq.sinajs.cn/list=s_sh000001";
	// 存放上证股票指数的txt文件
	String shangzhengFilePath = Constant.WEB_APP_ROOT + "/shangzheng.txt";
	// 拓日新能
	String tuoriURL = "http://hq.sinajs.cn/list=sz002218";
	String tuoriFilePath = Constant.WEB_APP_ROOT + "tuori.txt";
	// 大秦铁路
	String daqingURL = "http://hq.sinajs.cn/list=sh601006";
	String daqingFilePath = Constant.WEB_APP_ROOT + "daqing.txt";

	@Override
	public void init() throws ServletException {
		// 上证指数的序号
		map.put(shangzhengURL, new BigInteger("0"));
		// 拓日新能
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
		System.out.println("项目根目录1" + System.getProperty("root").replace("\\", "/"));
		System.out.println("项目根目录1" + getServletContext().getRealPath("/"));

		resp.setContentType("text/html;charset=utf-8");
		// 设置逻辑实现
		PrintWriter out = resp.getWriter();

		out.println("上证指数&nbsp;&nbsp;&nbsp;&nbsp;拓日新能<br/>");
		out.flush();
		while (true) {
			StringBuffer htmlMsg = new StringBuffer();
			htmlMsg.append(map.get(shangzhengURL) + "&nbsp;&nbsp;&nbsp;&nbsp;" + map.get(tuoriURL) + "<br/>");
			out.println(htmlMsg.toString());
			out.flush();
			// 如果不是交易时间则中断输出
			if (!SharesData.isTradingTime()) {
				out.println("现在不是不交易时间！！！<br/>");
				out.flush();
				out.close();
				break;
			}
			try {
				Thread.sleep(1000 * 10);
			} catch (InterruptedException e) {
				SharesData.logger.log(Level.WARNING, "输出的html的线程错误！！" + CustomerExceptionTool.getException(e));
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

}
