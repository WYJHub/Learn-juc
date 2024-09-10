package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageGenerator {
    private String audioPath;
    private String audioFileName;
    private String abovePath;

    public ImageGenerator(String path) {
        this.audioPath = new File("..\\..\\media", path).getAbsolutePath();
        this.audioFileName = new File(path).getName().split("\\.")[0];
        this.abovePath = new File(this.audioPath).getParent();
    }
    public static void main(String[] args) {
        // 创建一个宽度为300，高度为200，类型为RGB的BufferedImage对象
        BufferedImage image = new BufferedImage(300, 200, BufferedImage.TYPE_INT_RGB);

        // 获取Graphics2D对象
        Graphics2D g = image.createGraphics();

        // 设置背景颜色并填充
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());

        // 设置画笔颜色并绘制一个矩形
        g.setColor(Color.BLUE);
        g.drawRect(50, 50, 200, 100);
        // 释放资源
        g.dispose();

        // 将BufferedImage对象保存为文件
        try {
            ImageIO.write(image, "png", new File("output.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
