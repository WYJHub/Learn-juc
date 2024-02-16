import java.util.concurrent.TimeUnit;


//守护线程与用户线程

//通过setDaemon来设置线程的类型，该类型必须在线程start前设置，否则会抛出线程状态异常
//用户线程之间相互独立，互不干扰
//守护线程会随着其他线程执行完毕而随之结束
//垃圾回收处理器就是最经典的例子，在后台默默执行


//在一个django项目，一个post请求对应接口开启的线程会继承该线程的daemon属性，默认为用户线程，
//用户线程不会随着主线程的结束而结束
public class DaemonDemo {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "开始运行，" + 
            (Thread.currentThread().isDaemon() ? "守护线程" : "用户线程"));
            while(true) {

            }
        }, "t1");
        // t1.setDaemon(true);
        t1.start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " 主线程结束");
    }
}
