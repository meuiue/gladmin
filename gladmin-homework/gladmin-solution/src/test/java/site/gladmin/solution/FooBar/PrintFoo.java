package site.gladmin.solution.FooBar;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.interrupted;

class PrintFoo implements Runnable {


    @Override
    public void run() {
        System.out.print("Foo");
    }
}
