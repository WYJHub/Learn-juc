import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CompletableFutureMallDemo {


    static List<NetMall> list = Arrays.asList(new NetMall("jd"), new NetMall("taobao"),
            new NetMall("pdd"), new NetMall("tx"));

    public static List<String> getPrice(List<NetMall> list, String productName) {
        return list
                .stream()
                .map(netMall -> String.format("《" + productName + "》" + "in %s price %.2f",
                        netMall.getNetMallName(), netMall.calcPrice(productName)))
                .collect(Collectors.toList());
    }

    public static List<String> getPriceByCompletableFuture(List<NetMall> list, String productName) {
        return list
                .stream()
                .map(netMall -> CompletableFuture.supplyAsync(() -> String.format("《" + productName + "》" + "in %s price %.2f", netMall.getNetMallName(), netMall.calcPrice(productName)))).toList()
                .stream().map(CompletableFuture::join)
                .collect(Collectors.toList());
    }


    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        List<String> list1 = getPrice(list, "mysql");
        for(String element : list1) {
            System.out.println(element);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("costTime:" + (endTime - startTime) + " 毫秒");

        long startTime2 = System.currentTimeMillis();
        List<String> list2 = getPriceByCompletableFuture(list, "mysql");
        for(String element : list2) {
            System.out.println(element);
        }
        long endTime2 = System.currentTimeMillis();
        System.out.println("costTime:" + (endTime2 - startTime2) + " 毫秒");
    }


    //join与get区别为，get不抛异常会在编译阶段报错，join不会
    public static void m1() {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() ->
            "hello 1234");

        System.out.println(completableFuture.join());
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class NetMall {
    private String netMallName;

    //模拟查询接口
    public double calcPrice(String productName) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }
}