package volatiledemo;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class VolatileDemo {
    static boolean flag = true;
    static long i = 0;

    public static void main(String[] args) {
        
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t-------come in");
            long startTime = System.currentTimeMillis();
            while (flag) {
//                try {
//                    TimeUnit.MILLISECONDS.sleep(10);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
                i++;

            //    System.out.println(flag);
//                System.out.println(i);
            //    System.out.println(0);
            }
            System.out.println(i);
            long endTime = System.currentTimeMillis();
            System.out.println("costTime:" + (endTime - startTime) + " 毫秒");
            System.out.println(Thread.currentThread().getName() + "\t-------flag被设置为false，程序停止");
        }, "t1").start();

        try {
            TimeUnit.MILLISECONDS.sleep(20);
//            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //更新flag值
        flag = false;

        System.out.println(Thread.currentThread().getName() + "\t 修改完成");
    }
}
