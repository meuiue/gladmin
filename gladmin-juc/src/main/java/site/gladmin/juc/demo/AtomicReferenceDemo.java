package site.gladmin.juc.demo;


import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.concurrent.atomic.AtomicReference;

@Data
@AllArgsConstructor
class User{
    String userName;
    int age;
}

public class AtomicReferenceDemo {


    public static void main(String[] args) {


        User z3 = new User("z3",22);
        User l4 = new User("l4",25);

        AtomicReference<User> userAtomicReference = new AtomicReference<User>();

        userAtomicReference.set(z3);

        System.out.println(userAtomicReference.compareAndSet(z3,l4)+"\t" +userAtomicReference.get().toString());
        System.out.println(userAtomicReference.compareAndSet(z3,l4)+"\t" +userAtomicReference.get().toString());


        //时间戳的原子引用，解决ABA问题。
    }
}
