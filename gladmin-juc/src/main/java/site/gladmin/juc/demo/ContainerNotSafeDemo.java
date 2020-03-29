package site.gladmin.juc.demo;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 结合类不安全的问题
 */
public class ContainerNotSafeDemo {


    public static void main(String[] args) {


    }

    private static void ArrayListNotSafe() {
        ArrayList arrayList = new ArrayList<Integer>();
        arrayList.add(1);

        List<String> list = Arrays.asList("a","b","c");
        list.forEach(System.out::println);
    }

    private static void ListNotSafe() {
        //多线程安全问题来了
        /**
         * 1.用Vector解决；
         * 2.Collections.synchronizedList
         * 3.用juc包下CopyOnWriteArrayList
         */

        List<String> list =new CopyOnWriteArrayList<>();// Collections.synchronizedList(new ArrayList<>());// new ArrayList<>();

        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {

                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);

            }, String.valueOf(i)).start();
        }
    }

    private static void SetNotSafe() {
        //HashSet的判断线程安全 因为底层是HashMap，所以他是不能重复的，因为把值都存在了Key里，value为一个标准的object
//      Set<String> set = Collections.synchronizedSet(new HashSet<>());
        Set<String> set =new CopyOnWriteArraySet<>();
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }
}
