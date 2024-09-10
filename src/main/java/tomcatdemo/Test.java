package tomcatdemo;

import java.io.*;


public class Test {

    public static void main(String[] args) {
        System.out.println(0);
    }
//    public static void main(String[] args) throws IOException {
//        File file = new File(HttpServer.WEB_ROOT, "hello.txt");
//        int BUFFER_SIZE = 1023;
//        FileInputStream fis;
//        OutputStream output = new FileOutputStream("yxy");
//        byte[] bytes = new byte[BUFFER_SIZE];
//        if (file.exists()) {
//            fis = new FileInputStream(file);
//            int ch = fis.read(bytes, 0, BUFFER_SIZE);
//            while (ch != -1) {
//                output.write(bytes, 0, ch);
//                ch = fis.read(bytes, 0, BUFFER_SIZE);
//            }
//        }
//        System.out.println(output);
//    }
}
