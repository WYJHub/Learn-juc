package atomic;

import java.util.concurrent.atomic.AtomicStampedReference;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class Book {
    private Integer id;
    private String name;
}

//带邮戳的原子引用类，能够避免aba问题
public class AtomicStampleDemo {
    public static void main(String[] args) {
        Book book = new Book(1, "javabook");
        AtomicStampedReference<Book> atomicStampedReference = new AtomicStampedReference<Book>(book, 1);
        System.out.println(atomicStampedReference.getReference() + "\t" + atomicStampedReference.getStamp());
        
        Book gBook = new Book(2, "xhs");
        boolean b = atomicStampedReference.compareAndSet(book, gBook, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
        System.out.println(b + "\t" + atomicStampedReference.getReference() + "\t" + atomicStampedReference.getStamp());


        b = atomicStampedReference.compareAndSet(gBook, book, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
        System.out.println(b + "\t" + atomicStampedReference.getReference() + "\t" + atomicStampedReference.getStamp());
    }
}
