package tomcatdemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;

public class Response {

    private static final int BUFFER_SIZE = 1024;
    Request request;
    OutputStream output;

    public Response(OutputStream output) {
        this.output = output;
    }

    // response中封装了request，以便获取request中的请求参数
    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            // 读取文件内容
            File file = new File(HttpServer.WEB_ROOT, "hello.txt");
            if (file.exists()) {
                fis = new FileInputStream(file);
                String mimeType = URLConnection.getFileNameMap().getContentTypeFor("hello.txt");
                String header = "HTTP/1.1 200 OK\r\n"
                        + "Server: OneFile 2.0\r\n"
                        + "Content-type: " + mimeType + ";\r\n\r\n";
                System.out.println(header);
                output.write(header.getBytes());
                int ch = fis.read(bytes, 0, BUFFER_SIZE);
                while (ch != -1) {
                    output.write(bytes, 0, ch);
                    ch = fis.read(bytes, 0, BUFFER_SIZE);
                }
            }
            else {
                // 文件不存在时，输出404信息
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>";
                output.write(errorMessage.getBytes());
            }
        }
        catch (Exception e) {
            // thrown if cannot instantiate a File object
            System.out.println(e.toString() );
        }
        finally {
            if (fis!=null)
                fis.close();
        }
    }
}