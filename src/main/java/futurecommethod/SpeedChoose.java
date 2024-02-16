package futurecommethod;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SpeedChoose {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        CompletableFuture<String> taskA = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("A come in");
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "playA";
        }, executorService);

        CompletableFuture<String> taskB = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("B come in");
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "playB";
        }, executorService);


        CompletableFuture<String> completableFuture = taskA.applyToEither(taskB, f -> {
            return f + " is winner";
        });

        System.out.println(Thread.currentThread().getName() + "-------" + completableFuture.join());
        executorService.shutdown();
    }
}
