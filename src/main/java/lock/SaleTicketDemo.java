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