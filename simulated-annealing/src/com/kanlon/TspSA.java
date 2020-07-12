package com.kanlon;
 
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
 
public class TspSA {
 
    private int cityNum; // 城市数量，编码长度
    private int N;// 每个温度迭代步长
    private int T;// 降温次数
    private float a;// 降温系数
    private float t0;// 初始温度
 
    private int[][] distance; // 距离矩阵
    private int bestT;// 最佳出现代数
 
    private int[] Ghh;// 初始路径编码
    private int GhhEvaluation;
    private int[] bestGh;// 最好的路径编码
    private int bestEvaluation;
    private int[] tempGhh;// 存放临时编码
    private int tempEvaluation;
 
    private Random random;
 
    public TspSA() {
 
    }
 
    /**
     * constructor of GA
     * 
     * @param cn
     *            城市数量
     * @param t
     *            降温次数
     * @param n
     *            每个温度迭代步长
     * @param tt
     *            初始温度
     * @param aa
     *            降温系数
     * 
     **/
    public TspSA(int cn, int n, int t, float tt, float aa) {
        cityNum = cn;
        N = n;
        T = t;
        t0 = tt;
        a = aa;
    }
 
    // 给编译器一条指令，告诉它对被批注的代码元素内部的某些警告保持静默
    @SuppressWarnings("resource")
    /**
     * 初始化Tabu算法类
     * @param filename 数据文件名，该文件存储所有城市节点坐标数据
     * @throws IOException
     */
    private void init(String filename) throws IOException {
        // 读取数据
        int[] x;
        int[] y;
        String strbuff;
        BufferedReader data = new BufferedReader(new InputStreamReader(
                new FileInputStream(filename)));
        distance = new int[cityNum][cityNum];
        x = new int[cityNum];
        y = new int[cityNum];
        for (int i = 0; i < cityNum; i++) {
            // 读取一行数据，数据格式1 6734 1453
            strbuff = data.readLine();
            // 字符分割
            String[] strcol = strbuff.split(" ");
            x[i] = Integer.valueOf(strcol[1]);// x坐标
            y[i] = Integer.valueOf(strcol[2]);// y坐标
        }
        // 计算距离矩阵
        // ，针对具体问题，距离计算方法也不一样，此处用的是att48作为案例，它有48个城市，距离计算方法为伪欧氏距离，最优值为10628
        for (int i = 0; i < cityNum - 1; i++) {
            distance[i][i] = 0; // 对角线为0
            for (int j = i + 1; j < cityNum; j++) {
                double rij = Math
                        .sqrt(((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j])
                                * (y[i] - y[j])) / 10.0);
                // 四舍五入，取整
                int tij = (int) Math.round(rij);
                if (tij < rij) {
                    distance[i][j] = tij + 1;
                    distance[j][i] = distance[i][j];
                } else {
                    distance[i][j] = tij;
                    distance[j][i] = distance[i][j];
                }
            }
        }
        distance[cityNum - 1][cityNum - 1] = 0;
 
        Ghh = new int[cityNum];
        bestGh = new int[cityNum];
        bestEvaluation = Integer.MAX_VALUE;
        tempGhh = new int[cityNum];
        tempEvaluation = Integer.MAX_VALUE;
        bestT = 0;
        random = new Random(System.currentTimeMillis());
        
        System.out.println(cityNum+","+N+","+T+","+a+","+t0);
 
    }
 
    // 初始化编码Ghh
    void initGroup() {
        int i, j;
        Ghh[0] = random.nextInt(65535) % cityNum;
        for (i = 1; i < cityNum;)// 编码长度
        {
            Ghh[i] = random.nextInt(65535) % cityNum;
            for (j = 0; j < i; j++) {
                if (Ghh[i] == Ghh[j]) {
                    break;
                }
            }
            if (j == i) {
                i++;
            }
        }
    }
 
    // 复制编码体，复制编码Gha到Ghb
    public void copyGh(int[] Gha, int[] Ghb) {
        for (int i = 0; i < cityNum; i++) {
            Ghb[i] = Gha[i];
        }
    }
 
    public int evaluate(int[] chr) {
        // 0123
        int len = 0;
        // 编码，起始城市,城市1,城市2...城市n
        for (int i = 1; i < cityNum; i++) {
            len += distance[chr[i - 1]][chr[i]];
        }
        // 城市n,起始城市
        len += distance[chr[cityNum - 1]][chr[0]];
        return len;
    }
 
    // 邻域交换，得到邻居
    public void Linju(int[] Gh, int[] tempGh) {
        int i, temp;
        int ran1, ran2;
 
        for (i = 0; i < cityNum; i++) {
            tempGh[i] = Gh[i];
        }
        ran1 = random.nextInt(65535) % cityNum;
        ran2 = random.nextInt(65535) % cityNum;
        while (ran1 == ran2) {
            ran2 = random.nextInt(65535) % cityNum;
        }
        temp = tempGh[ran1];
        tempGh[ran1] = tempGh[ran2];
        tempGh[ran2] = temp;
    }
 
    public void solve() {
        // 初始化编码Ghh
        initGroup();
        copyGh(Ghh, bestGh);// 复制当前编码Ghh到最好编码bestGh
        bestEvaluation = evaluate(Ghh);
        GhhEvaluation = bestEvaluation;
        int k = 0;// 降温次数
        int n = 0;// 迭代步数
        float t = t0;
        float r = 0.0f;
        
        while (k < T) {
            n = 0;
            while (n < N) {
                Linju(Ghh, tempGhh);// 得到当前编码Ghh的邻域编码tempGhh
                tempEvaluation = evaluate(tempGhh);
                if (tempEvaluation < bestEvaluation)
                {
                    copyGh(tempGhh, bestGh);
                    bestT = k;
                    bestEvaluation = tempEvaluation;
                }
                r = random.nextFloat();
                if (tempEvaluation < GhhEvaluation
                        || Math.exp((GhhEvaluation - tempEvaluation) / t) > r) {
                    copyGh(tempGhh, Ghh);
                    GhhEvaluation = tempEvaluation;
                }
                n++;
            }
            t = a * t;
            k++;
        }
        
        System.out.println("最佳长度出现代数：");
        System.out.println(bestT);
        System.out.println("最佳长度");
        System.out.println(bestEvaluation);
        System.out.println("最佳路径：");
        for (int i = 0; i < cityNum; i++) {
            System.out.print(bestGh[i] + ",");
            if (i % 10 == 0 && i != 0) {
                System.out.println();
            }
        }
    }
 
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Start....");
        TspSA sa = new TspSA(48, 40, 400, 250.0f, 0.98f);
        sa.init("c://data.txt");
        sa.solve();
    }
}
