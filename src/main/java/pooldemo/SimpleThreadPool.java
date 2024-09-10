package pooldemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadLocalRandom;

class Worker implements Runnable {
    private int id;
    public Worker(int id) {
        this.id = id;
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + String.format(" worker%d ",id) + "start work");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + String.format(" worker%d ",id) + "end work");
    }
}

public class SimpleThreadPool {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5, new ThreadFactory(){
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });
        for(int i = 1; i < 10; i++) {
            Worker worker = new Worker(i);
            executor.execute(worker);
        }
        //
        executor.shutdown();
        while(!executor.isTerminated()) {

        }
        System.out.println("All Worker finished");
    }
}
