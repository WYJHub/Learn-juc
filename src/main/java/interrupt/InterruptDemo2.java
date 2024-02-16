package interrupt;

import java.util.concurrent.TimeUnit;

public class InterruptDemo2 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while(true) {
                if(Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "\t " +
                    "中断标记位：" + Thread.currentThread().isInterrupted() + " 程序停止");
                    break;
                }
                // 线程阻塞时调用interrupt方法会清除线程的中断状态，中断标志位设为true并抛出异常,需要重新调用interrupt方法
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread();
                    System.out.println("抛出异常后：" + Thread.interrupted());
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                System.out.println("Interrupt demo03");
            }
        }, "t1");

        t1.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            t1.interrupt();

            System.out.println("线程2设置t1" + t1.isInterrupted());
        }, "t2").start();
    }
}
