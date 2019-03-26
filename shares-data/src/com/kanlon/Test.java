package com.kanlon;

import java.io.File;

/**
 * 测试类
 *
 * @author zhangcanlong
 * @date 2018年11月7日
 */
public class Test {
	public static void main(String[] args) {
		System.out.println(SharesData.class.getResource("").getPath());
		System.out.println(SharesData.class.getResource("/").getPath());
		System.out.println(new File("").getAbsolutePath());
		System.out.println(Test.class.getClassLoader().getResource("/").getPath());
	}
}
