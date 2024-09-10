package threaddemo;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class ThreadCreate {
    public static void main(String[] args) throws Exception {
        new ThreadA().run();

        Object call = new ThreadB().call();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("run");
        },"t1").start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        },"t2").start();

    }
}

class ThreadA implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        System.out.println("启动A");
    }
}

class ThreadB implements Callable {

    @Override
    public Object call() throws Exception {
        System.out.println(Thread.currentThread().getName());
        Object o = new Object();
        return o;
    }
}
