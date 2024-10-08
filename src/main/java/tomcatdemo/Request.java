package tomcatdemo;

import java.io.IOException;
import java.io.InputStream;

// 请求是如何封装和响应的
public class Request {
    private InputStream input;
    private String uri;
    // 初始化Request
    public Request(InputStream input) {
        this.input = input;
    }
    // 处理request的方法
    public void parse() {
        // 从socket中读取字符
        StringBuffer request = new StringBuffer(2048);
        int i;
        byte[] buffer = new byte[2048];
        try {
            i = input.read(buffer);
        }
        catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }
        for (int j=0; j<i; j++) {
            request.append((char) buffer[j]);
        }
        System.out.println("Request=======================");
        System.out.print(request.toString());
        System.out.println("===================");
        // 获得两个空格之间的内容, 这里将是HttpServer.WEB_ROOT中静态文件的文件名称
        uri = parseUri(request.toString());
        System.out.println("uri" + uri);
    }

    private String parseUri(String requestString) {
        int index1 = -1, index2 = -1;
        index1 = requestString.indexOf(' ');
        System.out.println(index1);
        if (index1 != -1) {
            index2 = requestString.indexOf(' ', index1 + 1);
            System.out.println(index2);
            if (index2 > index1)
                return requestString.substring(index1 + 1, index2);
        }
        System.out.println("index");
        return null;
    }

    public String getUri() {
        return uri;
    }

}
