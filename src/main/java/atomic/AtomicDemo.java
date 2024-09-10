package atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.compareAndSet(5, 2022) + "\t" + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 2022) + "\t" + atomicInteger.get());
        /*
            get获取当前的值
            getAndSet(int newValue) 获取当前的值，并设置新的值
            getAndIncrement()
            getAndDecrement()
            getAndAdd(int delta)
            boolaen compareAndSet(int expect, int update)
            public final void lazySet(int newValue)最终设置为newValue,使用lazySet设置之后可能导致其他线程在之后的一小段时间内还是可以读到旧的值
        */
    }
}