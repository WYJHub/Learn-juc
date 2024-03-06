# 线程中断

AQS理解基础之一

如何中断一个线程

如何停止一个线程

## 为什么需要中断

首先
    一个线程不应该由其他线程来强制停止或中断，而是应该由线程自己自行停止，自己来决定自己的命运。所以 ，Thread.stop, Thread.suspend, Thread.resume都已经被废弃了。

其次
	在Java中没有办法立即停止一条线程，然而停止线程却显得尤为重要，如取消一个耗时操作。
	因此，Java提供了一种用于停止线程的协商机制一一中断，也即**中断标识协商机制**。中断只是一种协作协商机制，Java没有给中断增加任何语法，中断的过程完全需要程序员自己实现。

​	**若要中断一个线程，你需要手动调用该线程的interrupt方法，该方法也仅仅是将线程对象的中断标识设成true**;接着你需要自己写代码不断地检测当前线程的标识位，如果为true，表示别的线程请求这条线程中断，此时究竟该做什么需要你自己写代码实现。
​	每个线程对象中都有一个中断标识位，用于表示线程是否被中断;该标识位为true表示中断，为false表示未中断;通过调用线程对象的interrupt方法将该线程的标识位设为true; 可以在别的线程中调用，也可以在自己的线程中调用.

## 中断相关API

![image-20231117210325795](https://yingjun-typora.oss-cn-hangzhou.aliyuncs.com/img/202311172103934.png)

# 了解生产者消费者模式

待补充

## 如何实现线程中断

### 通过一个valatile变量来协商中断线程

```java
public class InterruptDemo {
    
    static volatile boolean isStop = false;
    public static void main(String[] args) {
        new Thread(() -> {
            while(true) {
                if(isStop) {
                    System.out.println(Thread.currentThread().getName() + "\t isStop被修改为true，程序停止");
                    break;
                }
                System.out.println("t1 ----------hello valatile");
            }
        }, "t1").start();

        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            isStop = true;
        }, "t2").start();
    }
}

```

### 通过AtomicBoolean实现

```java
public class InterruptDemo {
    
    static volatile boolean isStop = false;
    static AtomicBoolean auAtomicBoolean = new AtomicBoolean(false);
    public static void main(String[] args) {
        new Thread(() -> {
            while(true) {
                if(auAtomicBoolean.get()) {
                    System.out.println(Thread.currentThread().getName() + "\t auAtomicBoolean被修改为true，程序停止");
                    break;
                }
                System.out.println("t1 ----------hello atomicBoolean");
            }
        }, "t1").start();

        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            auAtomicBoolean.set(true);
        }, "t2").start();
    }
}
```

### 通过Thread类自带的中断API来实现

在需要中断的线程中不断监听中断状态，一旦发生中断，就执行相应的中断处理业务逻辑stop线程。

```java
public class InterruptDemo {
    
    static volatile boolean isStop = false;
    static AtomicBoolean auAtomicBoolean = new AtomicBoolean(false);
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while(true) {
                if(Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "\t interrupt标记位被修改为true，程序停止");
                    break;
                }
                System.out.println("t1 ----------hello intterupt api");
            }
        }, "t1");
        t1.start();
        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            t1.interrupt();
        }, "t2").start();
    }
}
```

具体来说，当对一个线程，调用 interrupt() 时:
1.如果线程处于正常活动状态，那么会将该线程的中断标志设置为 true，**仅此而已**。被设置中断标志的线程将继续正常运行，不受影响。所以， interrupt() 并不能真正的中断线程需要被调用的线程自己进行配合才行
2.如果线程处于被阻塞状态(例如处于sleep, wait, join 等状态)，在别的线程中调用当前线程对象的interrupt方法.那么线程将立即退出被阻塞状态，并抛出一个InterruptedException异常，注意此时并没有成功修改线程中断标记位。