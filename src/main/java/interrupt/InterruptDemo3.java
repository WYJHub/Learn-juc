package interrupt;

public class InterruptDemo3 {
    public static void main(String[] args) {
        
        //Thread.currentThread().isInterrupted() 返回线程中断标记位,不清除
        // Thread.interrupted()，返回线程中断标记位，并清除中断状态
        System.out.println(Thread.interrupted());
        System.out.println(Thread.interrupted());

        Thread.currentThread().interrupt();

        System.out.println(Thread.interrupted());
        System.out.println(Thread.interrupted());
    }
}
