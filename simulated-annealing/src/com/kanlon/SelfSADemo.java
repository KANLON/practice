package com.kanlon;

/**
 * 自己参考着实现的一个的sa算法的例子
 *
 * @author zhangcanlong
 * @since 2020/7/5 15:45
 **/
public class SelfSADemo {
    
    /**
     * 迭代次数
     */
    private static int num = 30000;

    /**
     * 函数中常数，可以为0
     */
    private static double k = 0.01;

    /**
     * 用于控制降温的快慢,r越大，降温越慢；r越小，降温越快
     */
    private static double r = 0.99;

    /**
     * 系统的温度，系统初始应该要处于一个高温的状态
     */
    private static double t = 200;

    /**
     * 温度的下限，若温度T达到T_min，则停止搜索,返回指定范围内的随机浮点数
     */
    private static final double tMin = 2;


    public static void main(String[] args) {
        double bestValue = targetFunc(RandomUtils.rnd(0, 2 * Math.PI));
        // 两个结果之间的差距
        double dE;
        // 当前值
        double current;
        // 计算一共降温了多少次
        int time = 0;
        while (t >= tMin) {
            for (int i = 0; i < num; ++i) {
                // 产生新的解
                current = targetFunc(RandomUtils.rnd(0, 2 * Math.PI));
                dE = current - bestValue;
                // 移动后得到更优的解，则继续移动
                if (dE < 0) {
                    bestValue = current;
                } else {
                    // 否则以一定概率继续移动,有一定概率接受差值
                    if (Math.exp(-dE / (t * k)) > RandomUtils.rnd(0.0, 1.0)) {
                        bestValue = current;
                    }
                }
            }
            // 降温退火 ，0<r<1 。r越大，降温越慢；r越小，降温越快
            t = r * t;
            time++;
        }
        System.out.println("当前函数的最小值为：" + bestValue + "；一共降温了：" + time + "次");
    }

    /**
     * 目标函数，
     *
     * @param x x值
     * @return double 结果值
     */
    public static double targetFunc(double x) {
        return 11 * Math.sin(6 * x) + 7 * Math.cos(5 * x);
    }


}
