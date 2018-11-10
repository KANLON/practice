package com.kanlon;

/**
 * 存放常量，例如路径之列的
 *
 * @author zhangcanlong
 * @date 2018年11月9日
 */
public class Constant {

	/**
	 * 项目类文件目录，相当于/WEB-INF/classes/之下的目录(window下，前面会多一个斜线，要去除前面一个斜线，linux下则不会，不知道什么原因)
	 */
	public static final String CLASS_PATH = SharesData.class.getClassLoader().getResource("").getPath().contains(":")
			? SharesData.class.getClassLoader().getResource("").getPath().substring(1)
			: SharesData.class.getClassLoader().getResource("").getPath();

	/**
	 * 项目根目录
	 */
	public static final String WEB_APP_ROOT = CLASS_PATH.replace("/WEB-INF/classes/", "/");

}
