import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureThreadPoolDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        long startTime = System.currentTimeMillis();
    
        FutureTask<String> futureTask1 = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "task1 over";
        });
        //new Thread方法会创建资源，GC垃圾处理器会回收，影响程序性能，采用线程池的方法就可以避免这个问题，尽可能去复用已有的线程
        // Thread t1 = new Thread(futureTask1, "t1"); //name是为了调试和监控做准备
        // t1.start();
        threadPool.submit(futureTask1);

        FutureTask<String> futureTask2 = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "task2 over";
        });
        threadPool.submit(futureTask2);
        
        System.out.println(futureTask1.get());
        System.out.println(futureTask2.get());
        FutureTask<String> futureTask3 = new FutureTask<>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "task3 over";
        });
        threadPool.submit(futureTask3);
        long endTime = System.currentTimeMillis();
        System.out.println("costTime:" + (endTime - startTime) + " 毫秒");


        System.out.println(Thread.currentThread().getName() + "\t -----end");
        threadPool.shutdown();
    }

    private static void m1() {

        //3个任务，只有一个线程main来处理，耗时 1136ms
        long startTime = System.currentTimeMillis();
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("costTime:" + (endTime - startTime) + " 毫秒");

        System.out.println(Thread.currentThread().getName() + "\t -----end");
    }
}
