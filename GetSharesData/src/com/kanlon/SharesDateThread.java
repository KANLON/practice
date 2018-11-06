package com.kanlon;

public class SharesDateThread {

	public static void main(String[] args) {
		// 上证股票指数
		String url = "http://hq.sinajs.cn/list=s_sh000001";
		// 存放上证股票指数的txt文件
		String shangzhengFilePath = "shangzheng.txt";

		// 拓日新能
		String tuoriURL = "http://hq.sinajs.cn/list=sz002218";
		String tuoriFilePath = "tuori.txt";

		// 大秦铁路
		String daqingURL = "http://hq.sinajs.cn/list=sh601006";
		String daqingFilePath = "daqing.txt";

		new Thread() {
			@Override
			public void run() {
				SharesData.getShangzhengSharesData(url, shangzhengFilePath);
			}
		}.start();
		new Thread() {
			@Override
			public void run() {
				SharesData.getOtherSharesData(tuoriURL, tuoriFilePath);
			}
		}.start();
	}

}
