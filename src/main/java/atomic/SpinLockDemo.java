package atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/*
 * CAS 是实现自旋锁的基础，CAS 利用 CPU 指令保证了操作的原子性
 * ，以达到锁的效果，至于自旋呢，看字面意思也很明白，自己旋转
 * 。是指尝试获取锁的线程不会立即阻案，而是采用循环的方式去尝试获取锁，
 * 当线程发现锁被占用时，会不断循环判断锁的状态，直到获取。
 * 这样的好处是减少线程上下文切换的消耗，缺点是循环会消耗CPU
 * 
 * 两点缺点；
 *  1.自旋锁轮询，会消耗CPU
 *  2.ABA问题，即线程A在获取资源A时，其内存值被另外一个线程修改并改回，在检查时无法发现异常，正常执行
 */
public class SpinLockDemo {
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void Lock() {
        Thread currentThread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t" + "come in");
        while(!atomicReference.compareAndSet(null, currentThread)) {

        }
    }

    public void unLock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + "\t" + "task over, unlock");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        
        new Thread(() -> {
            spinLockDemo.Lock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.unLock();
        }, "t1").start();

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            spinLockDemo.Lock();
            spinLockDemo.unLock();
        }, "t2").start();
    }
}
