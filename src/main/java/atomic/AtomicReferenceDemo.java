package atomic;

import java.util.concurrent.atomic.AtomicReference;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
class User {
    private String username;
    private String password;
}

public class AtomicReferenceDemo {
    public static void main(String[] args) {
        AtomicReference<User> atomicReference = new AtomicReference<>();
        User user = new User("张三", "as");
        User user2 = new User("李四", "ls");

        atomicReference.set(user);

        System.out.println(atomicReference.compareAndSet(user, user2) + "\t" + atomicReference.get().toString());

        System.out.println(atomicReference.compareAndSet(user, user2) + "\t" + atomicReference.get().toString());
    } 

}


