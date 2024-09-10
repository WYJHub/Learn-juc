package atomic;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

public class AtomicMap {

    public static Map<Integer, LongAdder> map = new HashMap<>();

    public static void main(String[] args) {
        map.put(1, new LongAdder());
        map.get(1).increment();
        map.get(1).increment();
        map.get(1).increment();
        System.out.println(map.get(1));
    }
}
