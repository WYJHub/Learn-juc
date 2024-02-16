# LockSupport是什么

LockSupport是用来创建锁和其他同步类的基本线程阻塞原语，其中park()和unpack()而作用分别是阻塞线程和解除阻塞线程.

## 线程等待唤醒机制

### 三种让线程等待和唤醒的方法

！！！注意三种方法的比较 

#### 方式一：SyncWaitNotify

- wait和notify方法必须要在同步代码块或者方法里面，且成对出现使用
- 先wait再notify才ok

- 使用Object中的wait()方法让线程等待，使用Object中的notify()方法唤醒线程

```java
public class LockSupportDemo {
    public static void main(String[] args) {
        Object object = new Object();

        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "---线程启动");
            synchronized(object) {
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "---线程被唤醒");
        }, "t1");
        t1.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            synchronized(object) {
                object.notify();
                System.out.println(Thread.currentThread().getName() + "---发出通知");
            }
        }, "t2").start();
    }    
}
```

##### 异常情况1

wait和notify都需要在线程持有目标锁的情况下调用，否则会抛出异常。

```
Exception in thread "t1" java.lang.IllegalMonitorStateException: current thread is not owner
        at java.base/java.lang.Object.wait(Native Method)
        at java.base/java.lang.Object.wait(Object.java:338)
        at locksupport.LockSupportDemo.lambda$0(LockSupportDemo.java:13)
        at java.base/java.lang.Thread.run(Thread.java:833)
```

##### 异常情况2

先调用notify，再调用wait，线程无法被唤醒

```java
public class LockSupportDemo {
    public static void main(String[] args) {
        Object object = new Object();
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "---线程启动");
            
            synchronized(object) {
                try {
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "---线程被唤醒");
        }, "t1");
        t1.start();

        new Thread(() -> {
            synchronized(object) {
                object.notify();
                System.out.println(Thread.currentThread().getName() + "---发出通知");
            }
        }, "t2").start();
    }    
}
```

#### 方式二：ReentrantLock Condition

使用JUC包中的Condition的await()方法让线程等待，使用signal()方法唤醒线程

```java
public class LockCondition {
    
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Condition newCondition = lock.newCondition();
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t ----come in");
                newCondition.await();
                System.out.println(Thread.currentThread().getName() + "\t ----被唤醒");
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "\t ----发起通知");
                newCondition.signal();
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }
}
```

异常情况，类似于上述sync中wait与notify方法，该方法也需要在持有锁时调用，即lock.lock()与lock.unlock()之间。

顺序也同样需要先await，后调用signal唤醒

#### Object和Condition使用的限制条件

- 线程需要先获得并持有锁，必须在锁块（synchronized或lock）中
- 必须要先等待后唤醒，线程才能够被唤醒

#### 方式三：LockSupport中park与unpark

LockSupport类可以阻塞当前线程以及唤醒指定被阻塞的线程。

不在受到原先锁块的要求和顺序的限制，可以提前设置unpark()

![image-20231120192521625](https://yingjun-typora.oss-cn-hangzhou.aliyuncs.com/img/202311201925731.png)

![image-20231120192547627](https://yingjun-typora.oss-cn-hangzhou.aliyuncs.com/img/202311201925684.png)