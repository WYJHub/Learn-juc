package locksupport;

import java.util.concurrent.TimeUnit;

public class SyncWaitNotify {
    public static void main(String[] args) {
        Object object = new Object();

        
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "---线程启动");
            
            
            synchronized(object) {
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "---线程被唤醒");
        }, "t1");
        t1.start();

        // try {
        //     TimeUnit.SECONDS.sleep(1);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

        new Thread(() -> {
            synchronized(object) {
                object.notify();
                System.out.println(Thread.currentThread().getName() + "---发出通知");
            }
        }, "t2").start();
    }    
}
