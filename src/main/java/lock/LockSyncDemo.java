package lock;

public class LockSyncDemo {
    Object o = new Object();

    public void m1() {
       
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
        new Object().notify();
    }
}
