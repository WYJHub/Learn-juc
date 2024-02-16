import java.util.concurrent.TimeUnit;

public class ThreadStateDemo {
    public static void main(String[] args) {
        Runtime runtime = Runtime.getRuntime();
		//获取当前电脑的CPU数量/核心数
		int cpuNums = runtime.availableProcessors();
		System.out.println("当前有CPU个数 "+cpuNums);//当前有CPU个数 8

        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "线程状态" + Thread.currentThread().getState());
            for(int i = 0; i < 200; i++) {
                System.out.println(i);
            }

        }, "t1");

        //创建未start时，为New，新建状态
        System.out.println("t1未start前:" + t1.getState());
        t1.start();
        System.out.println("t1刚start:" + t1.getState());
        Object o = new Object();
        Thread t2 = new Thread(() -> {
            try {
                t1.wait((long) o);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("t2执行");
            System.out.println("t1线程状态" + t1.getState());
            for(int i = 0; i < 200; i++) {
                System.out.println(i);
            }
        }, "t2");
        t2.start();
        
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("t1线程结束:" + t1.getState());
    }

}
