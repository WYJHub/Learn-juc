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
