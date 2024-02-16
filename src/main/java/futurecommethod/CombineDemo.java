package futurecommethod;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CombineDemo {
    public static void main(String[] args) {
        CompletableFuture<Integer> integerCompletableFuture1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "启动");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });

        CompletableFuture<Integer> integerCompletableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "启动");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 20;
        });


        CompletableFuture<Integer> finalResult = integerCompletableFuture1.thenCombine(integerCompletableFuture2, (x, y) -> {
            return x + y;
        });

        System.out.println(finalResult.join());
    }
}
