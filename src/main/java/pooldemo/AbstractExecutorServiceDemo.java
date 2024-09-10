package pooldemo;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

public class AbstractExecutorServiceDemo {
    public static void main(String[] args) {
        AbstractExecutorService abstractExecutorService = new AbstractExecutorService() {
            @Override
            public void execute(Runnable command) {

            }

            @Override
            public void shutdown() {

            }

            @Override
            public List<Runnable> shutdownNow() {
                return List.of();
            }

            @Override
            public boolean isShutdown() {
                return false;
            }

            @Override
            public boolean isTerminated() {
                return false;
            }

            @Override
            public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
                return false;
            }
        };
    }
}
