package com.kanlon;

public class SharesDateThread {

	public static void main(String[] args) {
		// ��֤��Ʊָ��
		String url = "http://hq.sinajs.cn/list=s_sh000001";
		// �����֤��Ʊָ����txt�ļ�
		String shangzhengFilePath = "shangzheng.txt";

		// ��������
		String tuoriURL = "http://hq.sinajs.cn/list=sz002218";
		String tuoriFilePath = "tuori.txt";

		// ������·
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
