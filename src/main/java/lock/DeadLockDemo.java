package lock;

import java.util.concurrent.TimeUnit;

public class DeadLockDemo {
    public static void main(String[] args) {
        final Object objectA = new Object();
        final Object objectB = new Object();

        new Thread(() -> {
            synchronized(objectA) {
                System.out.println(Thread.currentThread().getName() + "持有A期望获得B");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized(objectB) {
                    System.out.println(Thread.currentThread().getName() + "获得B");
                }
            }
        }, "a").start();
        new Thread(() -> {
            synchronized(objectB) {
                System.out.println(Thread.currentThread().getName() + "持有B期望获得A");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized(objectA) {
                    System.out.println(Thread.currentThread().getName() + "获得A");
                }
            }
        }, "b").start();
    }
}
