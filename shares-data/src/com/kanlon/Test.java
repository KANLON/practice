package com.kanlon;

import java.io.File;

/**
 * ������
 *
 * @author zhangcanlong
 * @date 2018��11��7��
 */
public class Test {
	public static void main(String[] args) {
		System.out.println(SharesData.class.getResource("").getPath());
		System.out.println(SharesData.class.getResource("/").getPath());
		System.out.println(new File("").getAbsolutePath());
		System.out.println(Test.class.getClassLoader().getResource("/").getPath());
	}
}
