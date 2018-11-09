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
 * �õ���Ʊ���ݵ��� <a>https://www.cnblogs.com/seacryfly/articles/stock.html<a>�ӿ�����
 *
 * @author zhangcanlong
 * @date 2018��11��6��
 */
public class SharesData {

	public static Logger logger = Logger.getLogger(SharesData.class.getName());

	static {
		try {
			// �����ڿ���̨���
			logger.setUseParentHandlers(true);
			// ������־����ȼ�
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
	 * post��������url���Ӳ��õ���Ӧ
	 *
	 * @param generalUrl
	 *            Ҫ�����url��ַ
	 * @param contentType
	 *            �������ݵ�����
	 * @param params
	 *            post�������͵Ĳ��� ���磺 userID=123&name=zhangsansan
	 * @param encoding
	 *            ����
	 * @return ��������url����Ӧ����ַ���
	 * @throws Exception
	 */
	public static String postGeneralUrl(String generalUrl, String contentType, String params, String encoding)
			throws Exception {
		URL url = new URL(generalUrl);
		// �򿪺�url֮�������
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		// ����ͨ�õ���������
		connection.setRequestProperty("Contect-Type", contentType);
		connection.setRequestProperty("Connection", "Keep-Alive");
		connection.setUseCaches(false);
		connection.setDoOutput(false);
		connection.setDoInput(true);

		// �õ���������������
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		out.write(params.getBytes(encoding));
		out.flush();
		out.close();

		// ����ʵ�ʵ�����
		connection.connect();
		// ��ȡ���е���Ӧͷ�ֶ�
		Map<String, List<String>> headers = connection.getHeaderFields();
		// �������е���Ӧͷ�ֶ�
		for (String key : headers.keySet()) {
			// System.err.println(key + "--->" + headers.get(key));
		}
		// ����bufferedReader����������ȡurl����Ӧ
		BufferedReader in = null;
		in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuffer buffer = new StringBuffer();
		String getLine;
		while ((getLine = in.readLine()) != null) {
			buffer.append(getLine);
		}
		in.close();
		// System.out.println("(url����Ӧ)result:" + buffer);

		return buffer.toString();
	}

	/**
	 * get��������url���Ӳ��õ���Ӧ
	 *
	 * @param generalUrl
	 *            Ҫ�����url��ַ
	 * @param contentType
	 *            �������ݵ�����
	 * @param encoding
	 *            ����
	 * @return ��������url����Ӧ����ַ���
	 * @throws Exception
	 */
	public static String getGeneralUrl(String generalUrl, String contentType, String params, String encoding)
			throws Exception {
		URL url = new URL(generalUrl);
		// �򿪺�url֮�������
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		// ����ͨ�õ���������
		connection.setRequestProperty("Contect-Type", contentType);
		connection.setRequestProperty("Connection", "Keep-Alive");
		connection.setUseCaches(false);
		connection.setDoOutput(false);
		connection.setDoInput(true);

		// ����ʵ�ʵ�����
		connection.connect();
		// ��ȡ���е���Ӧͷ�ֶ�
		Map<String, List<String>> headers = connection.getHeaderFields();
		// �������е���Ӧͷ�ֶ�
		for (String key : headers.keySet()) {
			// System.err.println(key + "--->" + headers.get(key));
		}
		// ����bufferedReader����������ȡurl����Ӧ
		BufferedReader in = null;
		in = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));
		StringBuffer buffer = new StringBuffer();
		String getLine;
		while ((getLine = in.readLine()) != null) {
			buffer.append(getLine);
		}
		in.close();
		// System.out.println("(url����Ӧ)result:" + buffer);
		return buffer.toString();
	}

	/**
	 * ���ַ���д�뵽�ı���
	 *
	 * @param contentStr
	 *            Ҫд�������
	 * @param path
	 *            Ҫд���ļ���·��
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
	 * ��ȡ��ָ֤�������ݲ�������ļ���
	 *
	 * @param url
	 *            ��֤��Ʊ�Ľӿ����ӣ����磺http://hq.sinajs.cn/list=s_sh000001 ��ʾ��ָ֤��
	 * @param filePath
	 *            Ҫ���ָ�����ݵ��ļ�
	 */
	public static void getShangzhengSharesData(String url, String filePath, Map<String, BigInteger> map) {
		String contentType = "text/html;charset=utf-8";
		String params = "";
		String encoding = "GBK";
		StringBuilder build = new StringBuilder();
		String title = "���,ʱ�������,��Ʊָ��ֵ,��������,�������Ȱٷֱ�" + System.lineSeparator();
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
						// ��Ʊָ��ֵ
						String data = results[1];
						// ��������
						String range = results[2];
						// �������Ȱٷֱ�
						String rangePercent = results[3];
						build.append(map.get(url).toString() + "," + new Date() + "," + data + "," + range + ","
								+ rangePercent + System.lineSeparator());
					}
					writeToFile(build.toString(), filePath);
					logger.log(Level.INFO, build.toString());
					// ������ļ�������
					build = new StringBuilder();
				} else {
					// ������ǽ���ʱ�䣬��˯��һ����
					Thread.sleep(1000 * 60);
				}
			} catch (Exception e) {
				logger.log(Level.WARNING, "��ȡ��ָ֤�����ݴ��󣡣���\r\n" + CustomerExceptionTool.getException(e));
			}
		}
	}

	/**
	 * ��ȡ������Ʊ�����ɣ������ݲ�������ļ���
	 *
	 * @param url
	 *            ĳ��Ʊ�Ľӿ����ӣ����磺http://hq.sinajs.cn/list=sh601006 ��ʾ������·
	 * @param filePath
	 *            Ҫ���ָ�����ݵ��ļ�
	 */
	public static void getOtherSharesData(String url, String filePath, Map<String, BigInteger> map) {
		String contentType = "text/html;charset=utf-8";
		String params = "";
		String encoding = "GBK";
		StringBuilder build = new StringBuilder();

		String title = "���,ʱ�������,���տ��̼�,�������̼�,��ǰ�۸�,������߼�,������ͼ�" + System.lineSeparator();
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
						// ���տ��̼�
						String openPrice = results[1];
						// �������̼�
						String yesterdayClosePrice = results[2];
						// ��ǰ�۸�
						String currentPrice = results[3];
						// ������߼�
						String maxPrice = results[4];
						// ������ͼ�
						String minPrice = results[5];
						build.append(map.get(url).toString() + "," + new Date() + "," + openPrice + ","
								+ yesterdayClosePrice + "," + currentPrice + "," + maxPrice + "," + minPrice
								+ System.lineSeparator());
					}
					writeToFile(build.toString(), filePath);
					logger.log(Level.INFO, build.toString());
					// ������ļ�������
					build = new StringBuilder();

				} else {
					// ������ǽ���ʱ�䣬��˯��һ����
					Thread.sleep(1000 * 60);
				}

			} catch (Exception e) {
				logger.log(Level.WARNING, "��ȡ�������ݴ��󣡣���\r\n" + CustomerExceptionTool.getException(e));
			}
		}
	}

	/**
	 * �ж��Ƿ�Ϊ���׵�ʱ�䣬�����գ����ڽ���ʱ����
	 *
	 * @return
	 */
	public static boolean isTradingTime() {
		Calendar cal = Calendar.getInstance();
		// ��ȡ�ж��Ƿ�Ϊ�ڼ���
		String date = "" + cal.get(Calendar.YEAR)
				+ ((cal.get(Calendar.MONTH) + 1) <= 9 ? ("0" + "" + (cal.get(Calendar.MONTH) + 1))
						: (cal.get(Calendar.MONTH) + 1))
				+ ((cal.get(Calendar.DATE) <= 9) ? "0" + cal.get(Calendar.DATE) : cal.get(Calendar.DATE));
		String dateURL = "http://api.goseek.cn/Tools/holiday?date=" + date;
		String isWorkDateStr = "";
		try {
			isWorkDateStr = getGeneralUrl(dateURL, "", "", "UTF-8");
		} catch (Exception e) {
			logger.log(Level.WARNING, "��ȡ����ʱ����󣡣���" + CustomerExceptionTool.getException(e));
			throw new RuntimeException("��ȡ����ʱ����󣡣���");
		}
		int flagDay = Integer.parseInt(isWorkDateStr.substring(isWorkDateStr.length() - 2, isWorkDateStr.length() - 1));

		if (flagDay != 0) {
			return false;
		}

		// �ж��Ƿ���9��30-12:00,13:00-15:00
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");

		try {
			Date now = df.parse(df.format(cal.getTime()));
			// ���Ե�ʱ��
			// now = df.parse("9:40");

			if (now.after(df.parse("9:30")) && now.before(df.parse("12:00"))
					|| (now.after(df.parse("13:00")) && now.before(df.parse("15:00")))) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("����ʱ����󣡣�");
		}

	}
}
