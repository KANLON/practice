package com.kanlon;

/**
 * ��ų���������·��֮�е�
 *
 * @author zhangcanlong
 * @date 2018��11��9��
 */
public class Constant {

	/**
	 * ��Ŀ���ļ�Ŀ¼���൱��/WEB-INF/classes/֮�µ�Ŀ¼(window�£�ǰ����һ��б�ߣ�Ҫȥ��ǰ��һ��б�ߣ�linux���򲻻ᣬ��֪��ʲôԭ��)
	 */
	public static final String CLASS_PATH = SharesData.class.getClassLoader().getResource("").getPath().contains(":")
			? SharesData.class.getClassLoader().getResource("").getPath().substring(1)
			: SharesData.class.getClassLoader().getResource("").getPath();

	/**
	 * ��Ŀ��Ŀ¼
	 */
	public static final String WEB_APP_ROOT = CLASS_PATH.replace("/WEB-INF/classes/", "/");

}
