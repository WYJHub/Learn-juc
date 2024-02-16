# 锁

## 1.乐观锁与悲观锁

乐观锁与悲观锁的区别在于使用数据时是否加锁。

对于同一个数据的操作，悲观锁认为自己在使用数据时，一定会有别的线程来修改数据，因此在获取数据时会先加锁，确保数据不会被别的线程修改。

synchronized关键字和Lock的实现类都是悲观锁。

而乐观锁则认为自己在使用数据时不会有别的线程来修改数据，所以不会添加锁，只是在更新数据的时候去判断之前有没有别的线程修改了数据。如果这个数据没有更新，则当前线程将自己修改的数据成功写入。如果数据已经被其他线程更新，则根据不同的实现方式执行不同的操作。

乐观锁的实现方式是通过版本号或者CAS算法来实现。

![image-20231115155949729](https://yingjun-typora.oss-cn-hangzhou.aliyuncs.com/img/202311151559874.png)

根据从上面的概念描述我们可以发现：

- **悲观锁适合写操作多的场景**，先加锁可以保证写操作时数据正确。
- **乐观锁适合读操作多的场景**，不加锁的特点能够使其读操作的性能大幅提升。



### Lock8Demo实例理解

```java
package lock;
import java.util.concurrent.TimeUnit;

/**
 * @author Guanghao Wei
 * @create 2023-04-10 14:57
 */

class Phone //资源类
{
    // static synchronized 类锁
    // synchronized 对象所
    public synchronized void sendEmail() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("------sendEmail");
    }

    public synchronized void sendSMS() {
        System.out.println("------sendSMS");
    }

    public void hello() {
        System.out.println("------hello");
    }
}

/**
 * 现象描述：
 * 1 标准访问ab两个线程，请问先打印邮件还是短信？ --------先邮件，后短信  共用一个对象锁
 * 2. sendEmail钟加入暂停3秒钟，请问先打印邮件还是短信？---------先邮件，后短信  共用一个对象锁
 * 3. 添加一个普通的hello方法，请问先打印普通方法还是邮件？ --------先hello，再邮件
 * 4. 有两部手机，请问先打印邮件还是短信？ ----先短信后邮件  资源没有争抢，不是同一个对象锁
 * 5. 有两个静态同步方法，一步手机， 请问先打印邮件还是短信？---------先邮件后短信  共用一个类锁
 * 6. 有两个静态同步方法，两部手机， 请问先打印邮件还是短信？ ----------先邮件后短信 共用一个类锁
 * 7. 有一个静态同步方法 一个普通同步方法，请问先打印邮件还是短信？ ---------先短信后邮件   一个用类锁一个用对象锁
 * 8. 有一个静态同步方法，一个普通同步方法，两部手机，请问先打印邮件还是短信？ -------先短信后邮件 一个类锁一个对象锁
 * 
 * 笔记总结：
 * 1 - 2
 * 一个对象里面如果有多个synchronized方法，某一个时刻内，只能有唯一的一个线程去调用其中的一个synchronized方法
 * 锁定的是当前对象this，被锁定后，其他的线程都不能进入到当前对象的其他的synchrnoized方法
 * 3 - 4
 * synchornized只和同一个实例内其他 有synchornized关键字的方法竞争
 * 注意是普通同步方法
 * 5 - 6
 * 普通同步方法synchronized 锁的是当前实例对象,this, 具体的一个资源类实例，所有的普通同步方法使用的都是同一把锁->实例对象本身
 * 静态同步方法static synchronized 锁的是当前类的class对象
 * 对于同步代码块, 锁的是synchronized括号内的对象
 * synchronized(o) {
 * 
 * }
 * 
 */

public class Lock8Demo {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendEmail();
        }, "a").start();

        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            // phone.sendSMS();
            phone.hello();
        }, "b").start();
    }

}
```



### 字节码角度理解Synchronized三种应用方式

- 作用于实例方法，当前实例加锁，进入同步代码块前要获得当前实例的锁；
- 作用于代码块，对括号里配置的对象加锁
- 作用于静态方法，当前类加锁，进去同步代码前要获得当前类对象的锁

```java
public class LockSyncDemo {
    
    public void m1() {
       Object o = new Object();
       synchronized(o) {
            System.out.println("-----hello synchronized m1");
       }
    }

    public synchronized void m2() {
        System.out.println("-----hello synchronized m2");
    }

    public static synchronized void m3() {
        System.out.println("-----hello static synchronized m3");
    }

    public static void main(String[] args) {
        
    }
}
```

使用IDE工具run一下，生成class文件，进入目标文件夹下，然后使用javap指令进行反编译：javap -v LockSyncDemo.class

```
javap -c *类名*.class
javap -v *类名*.class 带有附加信息
```

#### 1.同步代码块

对应m1方法，一个monitor enter对应两个monitor exit,前一个正常退出锁，后一个异常情况下退出锁

![image-20231115194200932](https://yingjun-typora.oss-cn-hangzhou.aliyuncs.com/img/202311151942089.png)

#### 2.普通同步方法

  调用指令将会检查方法的ACC_SYNCHRONIZED访问标志是否被设置，如果设置了，执行线程会将现持有monitor锁，然后再执行该方法，最后在方法完成（无论是否正常结束）时释放monitor。

![image-20231115194523529](https://yingjun-typora.oss-cn-hangzhou.aliyuncs.com/img/202311151945606.png)

#### 3.静态同步方法

  ACC_STATIC、ACC_SYNCHRONIZED访问标志区分该方法是否是静态同步方法。

![image-20231115194712197](https://yingjun-typora.oss-cn-hangzhou.aliyuncs.com/img/202311151947271.png)

# 面试题：为什么任何一个对象都可以成为一个锁？

![image-20231115195604343](https://yingjun-typora.oss-cn-hangzhou.aliyuncs.com/img/202311151956410.png)

![image-20231115195645334](https://yingjun-typora.oss-cn-hangzhou.aliyuncs.com/img/202311151956415.png)

JAVA中任何对象的父类均为Object，我们可以看到Object类中存在wait, notify，notifyAll等方法。

1. wait() 方法：
   - wait() 方法用于使当前线程等待，直到另一个线程调用该对象的 notify() 或 notifyAll() 方法来唤醒它。
   - 调用 wait() 方法会使当前线程进入等待状态，并释放对象锁，允许其他线程访问该对象。
   - wait() 方法通常与 synchronized 关键字配合使用，因为它要求当前线程必须拥有该对象的监视器（即对象锁）。
   - wait() 方法还可以指定超时时间，在等待一定时间后自动唤醒。
2. notify() 方法：
   - notify() 方法用于唤醒正在等待该对象的监视器的一个线程，如果有多个线程在等待，则只能唤醒其中的一个线程。
   - 调用 notify() 方法会随机选择一个等待线程进行唤醒，被唤醒的线程将开始尝试重新获取对象锁。
   - 如果没有正在等待的线程，notify() 方法不会产生任何效果。
3. notifyAll() 方法：
   - notifyAll() 方法用于唤醒正在等待该对象的监视器的所有线程。
   - 调用 notifyAll() 方法会唤醒所有等待线程，被唤醒的线程将开始尝试重新获取对象锁。

C++源码：ObjectMonitor.java--->ObjectMonitor.cpp--->ObjectMonitor.hpp

在C++源码中就定义了，每个对象天生都带着一个对象监视器，每一个被锁住的对象都会和Monitor关联起来。

指针指向Monitor对象（也称为管程或监视器）的真实地址。每个对象都存在着一个monitor与之关联，当一个monitor被某个线程持有后，它便处于锁定状态。在Java虚拟机（HotSpot）中，monitor是由OnjectMonitor实现的，其主要的数据结构如下

![image-20231115200304770](https://yingjun-typora.oss-cn-hangzhou.aliyuncs.com/img/202311152003869.png)



### 关于Synchronized关键字后续第12章内容

![image-20231115201800160](https://yingjun-typora.oss-cn-hangzhou.aliyuncs.com/img/202311152018309.png)

## 2.公平锁和非公平锁

本质特征，各个线程持有锁、获得锁、抢夺锁的概率的不同。

● 公平锁：是指多个线程按照申请锁的顺序来获取锁，这里类似于排队买票，先来的人先买，后来的人再队尾排着，这是公平的----- Lock lock = new ReentrantLock(true)---表示公平锁，先来先得。
● 非公平锁：是指多个线程获取锁的顺序并不是按照申请的顺序，有可能后申请的线程比先申请的线程优先获取锁，在高并发环境下，有可能造成优先级反转或者饥饿的状态（某个线程一直得不到锁）---- Lock lock = new  ReentrantLock(false)---表示非公平锁，后来的也可能先获得锁，默认为非公平锁。

```java
package lock;

import java.util.concurrent.locks.ReentrantLock;

class Ticket
{
    private int number = 50;
    ReentrantLock lock = new ReentrantLock(true); //默认是非公平锁, 设置为true则是公平锁

    public void sale() {
        lock.lock();
        try
        {
            if(number > 0) {
                System.out.println(Thread.currentThread().getName()+"卖出第：" + (number--) + "还剩下：" + number);
            }
        }
        finally{
            lock.unlock();
        }
    }
}

public class SaleTicketDemo {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(() -> {for(int i = 0; i < 55; i++) ticket.sale();}, "a").start();
        new Thread(() -> {for(int i = 0; i < 55; i++) ticket.sale();}, "b").start();
        new Thread(() -> {for(int i = 0; i < 55; i++) ticket.sale();}, "c").start();
    }
}
```



### 面试题：

### ● 为什么会有公平锁/非公平锁的设计？为什么默认非公平？

  ○ 恢复挂起的线程到真正锁的获取还是有时间差的，从开发人员来看这个时间微乎其微，但是从CPU的角度来看，这个时间差存在的还是很明显的。所以非公平锁能更充分地利用CPU的时间片，尽量减少CPU空间状态时间。
  ○ 使用多线程很重要的考量点是线程切换的开销，当采用非公平锁时，当一个线程请求锁获取同步状态，然后释放同步状态，所以刚释放锁的线程在此刻再次获取同步状态的概率就变得很大，所以就减少了线程的开销。

### ● 什么时候用公平？什么时候用非公平？

切换线程存在开销，非公平锁能有更高的吞吐量。

如果为了更高的吞吐量，很显然非公平锁是比较合适的，因为节省了很多线程切换的时间，吞吐量自然就上去了；否则就用公平锁，大家公平使用。

### 关于AQS

ReentrantLock中存在抽象队列同步器 AQS (AbstractQueueSynchronizer)

## 3.可重入锁（又名递归锁）

是指在同一线程在外层方法获取到锁的时侯，在进入该线程的内层方法会自动获取锁（前提，锁对象的是同一个对象），不会因为之前已经获取过还没释放而阻塞---------优点之一就是**可一定程度避免死锁**。



### 可重入锁种类

● 隐式锁（即synchronized关键字使用的锁），默认是可重入锁

  ○ 在一个synchronized修饰的方法或者代码块的内部调用本类的其他synchronized修饰的方法或者代码块时，是永远可以得到锁。

● 显式锁（即Lock）也有ReentrantLock这样的可重入锁

```java
/**
 * @author Guanghao Wei
 * @create 2023-04-10 16:05
 */
public class ReEntryLockDemo {

    public static void main(String[] args) {
        final Object o = new Object();
        /**
         * ---------------外层调用
         * ---------------中层调用
         * ---------------内层调用
         */
        new Thread(() -> {
            synchronized (o) {
                System.out.println("---------------外层调用");
                synchronized (o) {
                    System.out.println("---------------中层调用");
                    synchronized (o) {
                        System.out.println("---------------内层调用");
                    }
                }
            }
        }, "t1").start();

        /**
         * 注意：加锁几次就需要解锁几次
         * ---------------外层调用
         * ---------------中层调用
         * ---------------内层调用
         */
        Lock lock = new ReentrantLock();
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("---------------外层调用");
                lock.lock();
                try {
                    System.out.println("---------------中层调用");
                    lock.lock();
                    try {
                        System.out.println("---------------内层调用");
                    } finally {
                        lock.unlock();
                    }
                } finally {
                    lock.unlock();
                }
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }
}
```

### 可重入锁实现原理

每个锁对象拥有一个锁计数器和一个指向持有该锁的线程的指针。
当执行monitorenter时，如果目标锁对象的计数器为零，那么说明它没有被其他线程所持有，Java虚拟机会将该锁对象的持有线程设置为当前线程，并且将其计数器加1。
在目标锁对象的计数器不为零的情况下，如果锁对象的持有线程是当前线程，那么 Java 虚拟机可以将其计数器加1，否则需要等待，直至持有线程释放该锁。
当执行monitorexit时，Java虚拟机则需将锁对象的计数器减1。计数器为零代表锁已被释放。



在使用显式锁时，一定要lock与unlock数量对应，否则会出现锁不能释放。

![image-20231115200304770](https://yingjun-typora.oss-cn-hangzhou.aliyuncs.com/img/202311152003869.png)



## 4.死锁

死锁是指两个或两个以上的线程在执行过耀中,因争夺资源而造成的一种互相等待的现象,若无外力于涉那它们都将无法推进下去，如果
系统资源充足，进程的资源请求都能够得到满足，死锁出现的可能性就很低，否则就会因争夺有限的资源而陷入死锁。

![image-20231115213721069](https://yingjun-typora.oss-cn-hangzhou.aliyuncs.com/img/202311152137129.png)

```java
package lock;

import java.util.concurrent.TimeUnit;

public class DeadLockDemo {
    public static void main(String[] args) {
        final Object objectA = new Object();
        final Object objectB = new Object();

        new Thread(() -> {
            synchronized(objectA) {
                System.out.println(Thread.currentThread().getName() + "持有A期望获得B");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized(objectB) {
                    System.out.println(Thread.currentThread().getName() + "获得B");
                }
            }
        }, "a").start();
        new Thread(() -> {
            synchronized(objectB) {
                System.out.println(Thread.currentThread().getName() + "持有B期望获得A");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized(objectA) {
                    System.out.println(Thread.currentThread().getName() + "获得A");
                }
            }
        }, "b").start();
    }
}
```



### 如何排查死锁

#### 1.java原生命令

```
jps -l
jstack 进程编号
```

![image-20231115214849489](https://yingjun-typora.oss-cn-hangzhou.aliyuncs.com/img/202311152148525.png)

查看运行线程以及对应编号

然后使用jstack code，两者任意一个编号都可以。

![image-20231115215011773](https://yingjun-typora.oss-cn-hangzhou.aliyuncs.com/img/202311152150822.png)

#### 2.图形化

```
win + r输入
jconsole 打开java控制台
```

<img src="https://yingjun-typora.oss-cn-hangzhou.aliyuncs.com/img/202311152150680.png" alt="image-20231115215054625" style="zoom: 50%;" />



## 前面部分小总结

指针指向monitor对象(也称为管程或监视器锁)的起始地址。每个对象都存在着一个monitor与之关联，当一个 monitor 被某个线程持有后，它便处于锁定状态。在Java虚拟机(HotSpot)中，monitor是由ObjectMonitor实现的，其主要数据结构如下(位于HotSpot虚拟机源码ObjectMonitor.hpp文件，C++实现的)

![image-20231115215730207](https://yingjun-typora.oss-cn-hangzhou.aliyuncs.com/img/202311152157324.png)

![image-20231115215953631](https://yingjun-typora.oss-cn-hangzhou.aliyuncs.com/img/202311152159672.png)

## 2.自旋锁与适应性自旋锁