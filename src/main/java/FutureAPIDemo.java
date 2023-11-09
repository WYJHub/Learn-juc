import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


//结论
//Future对于结果的获取并不友好，只能过阻塞或轮询的方式得到任务的结果
public class FutureAPIDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName()+ "\t -----come in");
            
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "task over";
        });

        Thread t1 = new Thread(futureTask, "t1");
        t1.start();
        //get方法会阻塞主线后面的任务，调用get后，线程会一直等待直到异步任务结果返回，容易产生阻塞，一般建议调用需要放在程序片段末尾
        //不愿意等待太长时间，可以设定为过时不候
        // 模拟其他任务
        System.out.println(Thread.currentThread().getName()+ "\t -----忙其他任务");
        // System.out.println(futureTask.get(3, TimeUnit.SECONDS)); 可以用，不优雅

        //isDone
        //一般不会直接调用，轮询，查看任务是否完成，
        while (true) {
            if(futureTask.isDone()) {
                System.out.println(futureTask.get());
                break;
            }
            else {
                //设置查询间隙，减少CPU占用
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("正在处理中...");
            }
        }
    }
}
