import java.lang.Thread.State;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;


// 两个线程轮流打印1 - 100数字
// 线程a打印奇数， 线程b
public class PrintNum {

    public static int x = 0;
    public static boolean aRun = false;
    public static void main(String[] args) {
        // ReentrantLock lock = new ReentrantLock(true);
        
        Object o = new Object();

        Object oNotify = new Object();

        Thread a = new Thread(() -> {
            aRun = true;
            System.out.println("线程A启动");
            int i = 0;
            while(i++ < 50) {
                synchronized(o) {
                    x++;
                    System.out.println("a " + x);
                }
                LockSupport.park();
                synchronized(oNotify) {
                    oNotify.notify();
                }
            }
        });
        
        Thread b = new Thread(() -> {
            int i = 0;
            while(!aRun) {

            }
            System.out.println("线程A启动了再启动");
            while(i++ < 50) {
                synchronized(o) {
                    x++;
                    System.out.println("b " + x);
                }
                //阻塞b释放a
                LockSupport.unpark(a);
                synchronized(oNotify) {
                    try {
                        oNotify.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                
            }
        });
        b.start();
        a.start();
    }
    // public static void main(String[] args) {
    //     Object objectLock = new Object();

    //     Printer printer = new Printer();
    //     Thread b = new Thread(() -> {
    //         synchronized(objectLock) {
    //             while(!printer.aRun) {
    //                 try {
    //                     objectLock.wait();
    //                 } catch (InterruptedException e) {
    //                     e.printStackTrace();
    //                 }
    //             }
    //         }
    //         int i = 0;
    //         while(i++ < 50)
    //         {
    //             printer.add();
    //         }
    //     }, "b");
        
    //     Thread a = new Thread(() -> {
    //         synchronized(objectLock) {
    //             objectLock.notify();
    //             printer.setArun();
    //         }
    //         int i = 0;
    //         while(i++ < 50)
    //         {
    //             printer.add();
    //         }

    //     }, "a");
    //     a.start();
    //     b.start();
    //     System.out.println(b.getState());

    //     try {
    //         TimeUnit.SECONDS.sleep(1);
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }

    //     System.out.println(b.getState());
    //     // a.start();
    // }
}


class Printer{
    int x = 0;
    boolean aRun = false;
    ReentrantLock lock = new ReentrantLock(true);
    public void setArun() {
        this.aRun = true;
    }
    public void add() {
        lock.lock();
        this.x++;
        System.out.println(Thread.currentThread().getName() + " " + x);
        lock.unlock();
    }

    // public void print() {
    //     System.out.println(x);
    // }
}