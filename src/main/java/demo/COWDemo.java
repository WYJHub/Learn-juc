package demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class COWDemo {

    //随机可能重复的数组
    //这个数一定能找到
    //返回这个数对应的下标索引
    //随机访问一个
    //例如：有两个，保证随机的概率，
    //问一下：一定要随机出现？ 1 - 2， 或者 2 - 1
    //用一个map存储当前元素 -> 以及该元素对应的索引，每个从里面随机获取一个
    // 如何随机？ 可以存储一个变量保留位置，按顺序依次访问
    // 或者保留一个随机到当前元素的次数（），当数量到当前索引顺序的整数倍时，随机排序一次，然后再按顺序访问
    public static void main(String[] args) {
//        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
    }

    public int getNumRandomIndex(int target, int[] array) {
        int randomIndex = -1;
        Map<Integer, MyArray> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            MyArray myArray = map.getOrDefault(array[i], new MyArray());
            myArray.indexs.add(i);

        }
        return randomIndex;
    }
}


//LRUHashMap
//保留一个HashMap + LRU
//

//存储这个
class MyArray {
    List<Integer> indexs;
    int count; // count 为整数被
    public MyArray() {
        this.indexs = new ArrayList<>();
        this.count = 0;
    }
}
