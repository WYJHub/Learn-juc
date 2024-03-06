import java.util.concurrent.locks.LockSupport;


//可以利用线程间的通信机制来控制死锁
public class ThreadBaseDemo {
    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = new Object();
        Thread t1 = new Thread(() -> {
            System.out.println("线程1 come in");
            synchronized(o1) {
                System.out.println("线程1持有o1并等待");
                //持有锁o1
                LockSupport.park();
                //
                System.out.println("线程1尝试获取o2");
                synchronized(o2) {

                }
            }
        }, "t1");
        t1.start();
        
        //确定t1被锁住了,才开始获取锁
        Thread t2 = new Thread(() -> {
            // System.out.println(t1.getState());
            //持有o2后解锁前两个
            synchronized(o2) {
                System.out.println("线程2持有o2后给t1解锁");
                LockSupport.unpark(t1);
                synchronized(o1) {

                }
            }
        }, "t2");
        t2.start();
    }
}
