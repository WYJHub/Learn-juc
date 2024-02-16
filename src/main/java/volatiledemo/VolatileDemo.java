package volatiledemo;

import java.util.concurrent.TimeUnit;

public class VolatileDemo {
    
    static boolean flag = true;
    static int i = 0;
    public static void main(String[] args) {
        
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t-------come in");
            long startTime = System.currentTimeMillis();
            while (flag) {
            //    System.out.println(flag);
            //     System.out.println(i);
                // flag = flag;
                System.out.println(0);
            }
            long endTime = System.currentTimeMillis();
            System.out.println("costTime:" + (endTime - startTime) + " 毫秒");
            System.out.println(Thread.currentThread().getName() + "\t-------flag被设置为false，程序停止");
        }, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //更新flag值
        flag = false;

        System.out.println(Thread.currentThread().getName() + "\t 修改完成");
    }
}
