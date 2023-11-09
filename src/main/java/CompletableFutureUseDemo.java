import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CompletableFutureUseDemo {
    public static void main(String[] args){
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(3);
        
        CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "------come in");
            int result = ThreadLocalRandom.current().nextInt(10);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("一秒后出结果" + result);
//            if(result > 2) {
//                result = result / 0;
//            }
            return result;
        }, newFixedThreadPool).whenComplete((v, e) -> {
            if(e == null) {
                System.out.println("-----计算完成，更新系统updateVa：" + v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            System.out.println("异常情况：" + e.getCause() + "\t" + e.getMessage());
            return null;
        });
        System.out.println(Thread.currentThread().getName() + "线程先去忙其他任务");

        newFixedThreadPool.shutdown();
        //注意主线程不要立刻结束，否则CompletableFutur默认使用的线程池会立刻关闭：暂停三秒线程
        // try {
        //     TimeUnit.SECONDS.sleep(3);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
    }

    //CompletableFuture实现和Future一样的功能
    public static void future1() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName()+ "\t -----");
            int result = ThreadLocalRandom.current().nextInt(10);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("一秒后出结果" + result);
            return result;
        });

        System.out.println(Thread.currentThread().getName() + "线程先去忙其他任务");

        System.out.println(completableFuture.get());
    }
}
