package futuredemo;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

//同步与异步
//Future接口可以为主线程开一个分支任务，专门为主线程处理耗时和费力的复杂业务

//阻塞与非阻塞

//同步阻塞
//同步非阻塞
//异步阻塞
//异步非阻塞

public class CompletableFuturueDemo {
    //Future接口（FutureTask实现类）定义了操作异步任务执行一些方法，如获取异步任务的执行结果、取消任务的执行、判断任务是否被取消、判断任务是否执行完毕等
    
    //如果主线程需要执行一个耗时的计算任务，我们就可以通过future把这个任务放到异步线程中执行。
    //主线程继续处理其他任务或者先行结束，再通过Future获取计算结果

    //目的：
    //异步多线程任务执行且返回有结果，三个特点：多线程/有返回/异步任务
    
    //runnable future  => runnable子接口 runnableFuture 其对应实现类中有一个FutureTask，该类支持Callable接口，至此同时满足上述三个特点
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(new MyThread());
        Thread t1 = new Thread(futureTask, "t1");

        t1.start();

        //调用get方法后，该线程会阻塞。
        System.out.println(futureTask.get());
    }
}


//thread与thread2对比， runnable和callable接口对比
//runnable接口实现run方法，没有抛异常，没有返回值
//callable接口实现call方法，抛异常，有返回值
// class MyThread implements Runnable {

//     @Override
//     public void run() {
        
//     }

// }

class MyThread implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("------come in call()");
        return "hello callable";
    }
    
}