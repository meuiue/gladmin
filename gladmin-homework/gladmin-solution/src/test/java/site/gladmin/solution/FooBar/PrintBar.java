package site.gladmin.solution.FooBar;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.interrupted;

public class PrintBar implements Runnable {

    @Override
    public void run() {
        System.out.println("bar");
    }
}
