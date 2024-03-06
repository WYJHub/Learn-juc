package atomic;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
class User {
    private String username;
    private String password;
}

public class AtomicMarkableReferenceDemo {

    public static void main(String[] args) {
        AtomicMarkableReference<User> atomicMarkableReference = new AtomicMarkableReference<>(null, false);
        User user = new User("张三", "as");
        User user2 = new User("李四", "ls");

        atomicMarkableReference.set(user, true);
        //判断其中的AtomicMarkableReference中的值是否还是user，如果是，则切换为user2
        System.out.println(atomicMarkableReference.compareAndSet(user, user2, true, true) + "\t" + atomicMarkableReference.getReference().toString());
        //当前其中已经不是user了，所以自旋失败，不交换，依旧是user1
        System.out.println(atomicMarkableReference.compareAndSet(user, user2, true, true) + "\t" + atomicMarkableReference.getReference().toString());
    }
}
