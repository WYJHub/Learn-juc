package interrupt;

import java.util.concurrent.TimeUnit;

public class InterruptDemo1 {
    
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for(int i = 0; i <= 300; i++) {
                System.out.println("------" + i);
            }
            System.out.println("t1线程调用interrupt()后的中断标识02：" + Thread.currentThread().isInterrupted());
        }, "t1");
        t1.start();
        System.out.println(t1.getState());
        System.out.println("t1线程默认的中断标识:" + t1.isInterrupted());

        try {
            TimeUnit.MILLISECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
        System.out.println("t1线程调用interrupt()后的中断标识01:" + t1.isInterrupted());
        System.err.println(t1.getState());
        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("t1线程调用interrupt()后的中断标识03:" + t1.isInterrupted());
        System.err.println(t1.getState());
    }
}
