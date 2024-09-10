package demo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ObjectDemo {

    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Object object = new Object();
//        Class<?> aClass = object.getClass();
        User user1 = new User();
        user1.setUsername("张三");
        user1.setPassword("111");
        User user2 = new User();
        user2.setUsername("张三");
        user2.setPassword("111");
        System.out.println(user1.equals(user2));
        Class<? extends User> aClass = user1.getClass();
        System.out.println(aClass.getName());

//        User user = constructor.newInstance("张三", "11");
//        System.out.println(user);
    }


}

class User {
    private String username;
    private String password;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public User() {
        System.out.println("空构造函数");
    }
    public User(String username, String password) {
        System.out.println("构造函数");
        this.username = username;
        this.password = password;
    }
    public String toString() {
        return "111";
    }


    public boolean equals(User obj) {
        System.out.println(this);
        System.out.println(this.hashCode());
        System.out.println(System.identityHashCode(this));
//        System.out.println(obj.getClass());
        System.out.println(obj.hashCode());
        System.out.println(System.identityHashCode(obj));
        System.out.println(obj);
        return (this == obj);

    }
}
