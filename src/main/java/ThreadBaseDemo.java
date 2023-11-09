public class ThreadBaseDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {

        }, "t1");
        thread.start();
    }
}
