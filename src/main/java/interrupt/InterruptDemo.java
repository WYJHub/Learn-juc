package interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class InterruptDemo {
    
    static volatile boolean isStop = false;
    static AtomicBoolean auAtomicBoolean = new AtomicBoolean(false);
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while(true) {
                if(Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "\t interrupt标记位被修改为true，程序停止");
                    break;
                }
                System.out.println("t1 ----------hello intterupt api");
            }
        }, "t1");
        t1.start();
        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            t1.interrupt();
        }, "t2").start();
    }

    public static void m2_atomicBoolean() {
        new Thread(() -> {
            while(true) {
                if(auAtomicBoolean.get()) {
                    System.out.println(Thread.currentThread().getName() + "\t auAtomicBoolean被修改为true，程序停止");
                    break;
                }
                System.out.println("t1 ----------hello atomicBoolean");
            }
        }, "t1").start();

        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            auAtomicBoolean.set(true);
        }, "t2").start();
    }
    
    public void m1() {
        new Thread(() -> {
            while(true) {
                if(isStop) {
                    System.out.println(Thread.currentThread().getName() + "\t isStop被修改为true，程序停止");
                    break;
                }
                System.out.println("t1 ----------hello valatile");
            }
        }, "t1").start();

        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            isStop = true;
        }, "t2").start();
    }
}
