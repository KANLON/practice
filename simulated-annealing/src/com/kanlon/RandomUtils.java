package com.kanlon;

import java.util.Random;

/**
 * 随机数的工具类
 *
 * @author zhangcanlong
 * @since 2020/7/5 16:36
 **/
public class RandomUtils {

    private RandomUtils() {
        
    }

    /**
     * 产生 两者之间的随机数
     *
     * @param dbLow   小的数
     * @param dbUpper 大的数据
     * @return double 两者间的随机数
     */
    public static double rnd(double dbLow, double dbUpper) {
        double tempD = new Random().nextDouble();
        return dbLow + (dbUpper - dbLow) * tempD;
    }

}
