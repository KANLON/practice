package com.kanlon;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * 得到股票数据的类 <a>https://www.cnblogs.com/seacryfly/articles/stock.html<a>接口内容
 *
 * @author zhangcanlong
 * @date 2018年11月6日
 */
public class SharesData {

	public static Logger logger = Logger.getLogger(SharesData.class.getName());

	static {
		try {
			// 设置在控制台输出
			logger.setUseParentHandlers(true);
			// 设置日志输出等级
			logger.setLevel(Level.INFO);
			FileHandler fileHandler = null;
			fileHandler = new FileHandler(Constant.WEB_APP_ROOT + "/logs/"
					+ new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()) + ".log");
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			fileHandler.setFormatter(new Formatter() {
				@Override
				public String format(LogRecord arg0) {
					return String.format("%-8s", arg0.getLevel().getLocalizedName())
							+ sdf.format(new Date(arg0.getMillis())) + "  : " + arg0.getMessage() + "\n";
				}
			});
			logger.addHandler(fileHandler);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private final static BigInteger ONE = new BigInteger("1");

	/**
	 * post方法请求url链接并得到响应
	 *
	 * @param generalUrl
	 *            要请求的url地址
	 * @param contentType
	 *            请求内容的类型
	 * @param params
	 *            post方法发送的参数 例如： userID=123&name=zhangsansan
	 * @param encoding
	 *            编码
	 * @return 返回请求url的响应结果字符串
	 * @throws Exception
	 */
	public static String postGeneralUrl(String generalUrl, String contentType, String params, String encoding)
			throws Exception {
		URL url = new URL(generalUrl);
		// 打开和url之间的链接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		// 设置通用的请求属性
		connection.setRequestProperty("Contect-Type", contentType);
		connection.setRequestProperty("Connection", "Keep-Alive");
		connection.setUseCaches(false);
		connection.setDoOutput(false);
		connection.setDoInput(true);

		// 得到请求的输出流对象
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		out.write(params.getBytes(encoding));
		out.flush();
		out.close();

		// 建立实际的连接
		connection.connect();
		// 获取所有的响应头字段
		Map<String, List<String>> headers = connection.getHeaderFields();
		// 遍历所有的响应头字段
		for (String key : headers.keySet()) {
			// System.err.println(key + "--->" + headers.get(key));
		}
		// 定义bufferedReader输入流来读取url的响应
		BufferedReader in = null;
		in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuffer buffer = new StringBuffer();
		String getLine;
		while ((getLine = in.readLine()) != null) {
			buffer.append(getLine);
		}
		in.close();
		// System.out.println("(url的响应)result:" + buffer);

		return buffer.toString();
	}

	/**
	 * get方法请求url链接并得到响应
	 *
	 * @param generalUrl
	 *            要请求的url地址
	 * @param contentType
	 *            请求内容的类型
	 * @param encoding
	 *            编码
	 * @return 返回请求url的响应结果字符串
	 * @throws Exception
	 */
	public static String getGeneralUrl(String generalUrl, String contentType, String params, String encoding)
			throws Exception {
		URL url = new URL(generalUrl);
		// 打开和url之间的链接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		// 设置通用的请求属性
		connection.setRequestProperty("Contect-Type", contentType);
		connection.setRequestProperty("Connection", "Keep-Alive");
		connection.setUseCaches(false);
		connection.setDoOutput(false);
		connection.setDoInput(true);

		// 建立实际的连接
		connection.connect();
		// 获取所有的响应头字段
		Map<String, List<String>> headers = connection.getHeaderFields();
		// 遍历所有的响应头字段
		for (String key : headers.keySet()) {
			// System.err.println(key + "--->" + headers.get(key));
		}
		// 定义bufferedReader输入流来读取url的响应
		BufferedReader in = null;
		in = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));
		StringBuffer buffer = new StringBuffer();
		String getLine;
		while ((getLine = in.readLine()) != null) {
			buffer.append(getLine);
		}
		in.close();
		// System.out.println("(url的响应)result:" + buffer);
		return buffer.toString();
	}

	/**
	 * 将字符串写入到文本中
	 *
	 * @param contentStr
	 *            要写入的内容
	 * @param path
	 *            要写入文件的路径
	 * @throws IOException
	 */
	public static void writeToFile(String contentStr, String filePath) throws IOException {
		File file = new File(filePath);
		logger.info(file.getAbsolutePath());
		FileWriter writer = new FileWriter(file, true);
		writer.write(contentStr);
		writer.flush();
		writer.close();
	}

	/**
	 * 获取上证指数的数据并输出到文件中
	 *
	 * @param url
	 *            上证股票的接口链接，例如：http://hq.sinajs.cn/list=s_sh000001 表示上证指数
	 * @param filePath
	 *            要存放指数数据的文件
	 */
	public static void getShangzhengSharesData(String url, String filePath, Map<String, BigInteger> map) {
		String contentType = "text/html;charset=utf-8";
		String params = "";
		String encoding = "GBK";
		StringBuilder build = new StringBuilder();
		String title = "序号,时间毫秒数,股票指数值,浮动幅度,浮动幅度百分比" + System.lineSeparator();
		logger.log(Level.INFO, title);
		try {
			writeToFile(title, filePath);
		} catch (IOException e1) {
			logger.log(Level.SEVERE, CustomerExceptionTool.getException(e1));
		}
		while (true) {
			try {

				if (isTradingTime()) {
					for (int i = 0; i < 10; i++) {
						Thread.sleep(1000 * 10);
						map.put(url, map.get(url).add(ONE));
						String[] results = getGeneralUrl(url, contentType, params, encoding).split(",");
						// 股票指数值
						String data = results[1];
						// 浮动幅度
						String range = results[2];
						// 浮动幅度百分比
						String rangePercent = results[3];
						build.append(map.get(url).toString() + "," + new Date() + "," + data + "," + range + ","
								+ rangePercent + System.lineSeparator());
					}
					writeToFile(build.toString(), filePath);
					logger.log(Level.INFO, build.toString());
					// 输出到文件后重置
					build = new StringBuilder();
				} else {
					// 如果不是交易时间，则睡眠一分钟
					Thread.sleep(1000 * 60);
				}
			} catch (Exception e) {
				logger.log(Level.WARNING, "获取上证指数数据错误！！！\r\n" + CustomerExceptionTool.getException(e));
			}
		}
	}

	/**
	 * 获取其他股票（个股）的数据并输出到文件中
	 *
	 * @param url
	 *            某股票的接口链接，例如：http://hq.sinajs.cn/list=sh601006 表示大秦铁路
	 * @param filePath
	 *            要存放指数数据的文件
	 */
	public static void getOtherSharesData(String url, String filePath, Map<String, BigInteger> map) {
		String contentType = "text/html;charset=utf-8";
		String params = "";
		String encoding = "GBK";
		StringBuilder build = new StringBuilder();

		String title = "序号,时间毫秒数,今日开盘价,昨日收盘价,当前价格,今日最高价,今日最低价" + System.lineSeparator();
		System.out.print(title);
		try {
			writeToFile(title, filePath);
		} catch (IOException e1) {
			logger.log(Level.SEVERE, CustomerExceptionTool.getException(e1));
		}
		while (true) {
			try {

				if (isTradingTime()) {
					for (int i = 0; i < 10; i++) {
						Thread.sleep(1000 * 10);
						map.put(url, map.get(url).add(ONE));
						String[] results = getGeneralUrl(url, contentType, params, encoding).split(",");
						// 今日开盘价
						String openPrice = results[1];
						// 昨日收盘价
						String yesterdayClosePrice = results[2];
						// 当前价格
						String currentPrice = results[3];
						// 今日最高价
						String maxPrice = results[4];
						// 今日最低价
						String minPrice = results[5];
						build.append(map.get(url).toString() + "," + new Date() + "," + openPrice + ","
								+ yesterdayClosePrice + "," + currentPrice + "," + maxPrice + "," + minPrice
								+ System.lineSeparator());
					}
					writeToFile(build.toString(), filePath);
					logger.log(Level.INFO, build.toString());
					// 输出到文件后重置
					build = new StringBuilder();

				} else {
					// 如果不是交易时间，则睡眠一分钟
					Thread.sleep(1000 * 60);
				}

			} catch (Exception e) {
				logger.log(Level.WARNING, "获取各股数据错误！！！\r\n" + CustomerExceptionTool.getException(e));
			}
		}
	}

	/**
	 * 判断是否为交易的时间，工作日，且在交易时间内
	 *
	 * @return
	 */
	public static boolean isTradingTime() {
		Calendar cal = Calendar.getInstance();
		// 获取判断是否为节假日
		String date = "" + cal.get(Calendar.YEAR)
				+ ((cal.get(Calendar.MONTH) + 1) <= 9 ? ("0" + "" + (cal.get(Calendar.MONTH) + 1))
						: (cal.get(Calendar.MONTH) + 1))
				+ ((cal.get(Calendar.DATE) <= 9) ? "0" + cal.get(Calendar.DATE) : cal.get(Calendar.DATE));
		String dateURL = "http://api.goseek.cn/Tools/holiday?date=" + date;
		String isWorkDateStr = "";
		try {
			isWorkDateStr = getGeneralUrl(dateURL, "", "", "UTF-8");
		} catch (Exception e) {
			logger.log(Level.WARNING, "获取交易时间错误！！！" + CustomerExceptionTool.getException(e));
			throw new RuntimeException("获取交易时间错误！！！");
		}
		int flagDay = Integer.parseInt(isWorkDateStr.substring(isWorkDateStr.length() - 2, isWorkDateStr.length() - 1));

		if (flagDay != 0) {
			return false;
		}

		// 判断是否在9：30-12:00,13:00-15:00
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");

		try {
			Date now = df.parse(df.format(cal.getTime()));
			// 测试的时间
			// now = df.parse("9:40");

			if (now.after(df.parse("9:30")) && now.before(df.parse("12:00"))
					|| (now.after(df.parse("13:00")) && now.before(df.parse("15:00")))) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("解析时间错误！！");
		}

	}
}
