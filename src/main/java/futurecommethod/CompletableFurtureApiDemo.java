package futurecommethod;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *     ■ thenRun(Runnable runnable) :任务A执行完执行B，并且不需要A的结果
 *     ■ thenAccept(Consumer action): 任务A执行完执行B，B需要A的结果，但是任务B没有返回值
 *     ■ thenApply(Function fn): 任务A执行完执行B，B需要A的结果，同时任务B有返回值
 */
public class CompletableFurtureApiDemo {

    public static void main(String[] args) {
        System.out.println(CompletableFuture.supplyAsync(() -> "result").thenRun(() -> {}).join());//null
        System.out.println(CompletableFuture.supplyAsync(() -> "result").thenAccept(r -> System.out.println(r)).join());//result null
        System.out.println(CompletableFuture.supplyAsync(() -> "result").thenApply(f -> f + "1").join());//result2
    }
//    public static void main(String[] args) {
//        ExecutorService threadPool = Executors.newFixedThreadPool(3);
//        CompletableFuture.supplyAsync(() -> {
//            return 1;
//        }, threadPool).thenApply(f -> {
//            return f + 2;
//        }).thenApply(f -> {
//            return f + 2;
//        }).thenAccept(r -> {
//            System.out.println(r);//5
//        });
//    }
}
