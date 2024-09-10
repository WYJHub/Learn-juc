package demo;

import java.util.*;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class HashSetDemo {

    //ConcurrentHashMap如何做到线程安全

    public static void main(String[] args) {
        HashMap<Integer, Integer> map = new HashMap<>();
        ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();

        ArrayList<Integer> list = new ArrayList<>();
        //获取CPU数量
        int NCPU = Runtime.getRuntime().availableProcessors();
        System.out.println(NCPU);
        //static class 静态内部类,只有在节点类被使用时，才会第一次加载Node<K,V>，且该过程是线程安全的

        String key = "213123";
        int h = key.hashCode();
        int hash = h ^ (h >> 16);//hash扰动，减轻使得hash落点更加的均匀，合并高16位数字的影响
        //如果不扰动，高位数字可能永远无法影响到对应node的落点位。例如二进制：111000（B） / 8(O)

        //tableSizeFor(int c)获得当前容量对应的2的幂次

        //相比于hashMap，由volatile

//        Vector
        LinkedList<Integer> list2 = new LinkedList<>();

        //TreeMap适用于范围搜索
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        Integer.parseInt("213");

        BlockingDeque<Integer> queue = new LinkedBlockingDeque<>();

    }
}
