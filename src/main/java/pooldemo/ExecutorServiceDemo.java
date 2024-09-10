package pooldemo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

class CallableWorker implements Callable<Integer> {
    private int time;
    public CallableWorker(int time) {
        this.time = time;
    }
    @Override
    public Integer call() throws Exception {
        System.out.println(time + "正在运行");
        TimeUnit.SECONDS.sleep(time);
        return this.time;
    }
}

public class ExecutorServiceDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<CallableWorker> callables = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            callables.add(new CallableWorker(10));
        }
        List<Future<Integer>> futures = executorService.invokeAll(callables, 5, TimeUnit.SECONDS);
        for (Future<Integer> future : futures) {
            System.out.println(future.isDone() + " " + future.get());
        }
//        Integer i = executorService.invokeAny(callables,5, TimeUnit.SECONDS);
//        System.out.println(i);
//        executorService.shutdown();
    }
}
