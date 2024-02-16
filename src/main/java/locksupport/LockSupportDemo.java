package locksupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo {
    public static void main(String[] args) {
        
        Thread a = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "--- come in");
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "--- 被唤醒");
        }, "t1");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread b = new Thread(() -> {
            LockSupport.unpark(a);
            System.out.println(Thread.currentThread().getName() + "---发起通知");
        },"t2");
        a.start();
        b.start();
    }
}
