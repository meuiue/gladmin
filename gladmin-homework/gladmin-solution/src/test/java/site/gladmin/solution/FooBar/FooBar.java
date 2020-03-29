package site.gladmin.solution.FooBar;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * 两个不同的线程将会共用一个 FooBar 实例。其中一个线程将会调用 foo() 方法，另一个线程将会调用 bar() 方法。
 *
 * 请设计修改程序，以确保 "foobar" 被输出 n 次。
 *
 *
 * 示例 1:
 *
 * 输入: n = 1
 * 输出: "foobar"
 * 解释: 这里有两个线程被异步启动。其中一个调用 foo() 方法, 另一个调用 bar() 方法，"foobar" 将被输出一次。
 * 示例 2:
 *
 * 输入: n = 2
 * 输出: "foobarfoobar"
 * 解释: "foobar" 将被输出两次。
 */
class FooBar {

    private CountDownLatch countDownLatch;
    private CyclicBarrier cyclicBarrier;


    private int n;

    public FooBar(int n) {
        this.n = n;

        countDownLatch = new CountDownLatch(1);
        cyclicBarrier = new CyclicBarrier(2);
    }

    public void foo(Runnable printFoo) throws InterruptedException, BrokenBarrierException {

        for (int i = 0; i < n; i++) {

            // printFoo.run() outputs "foo". Do not change or remove this line.

            printFoo.run();

            countDownLatch.countDown();
            cyclicBarrier.await();

        }
    }

    public void bar(Runnable printBar) throws InterruptedException, BrokenBarrierException {

        for (int i = 0; i <= n; i++) {

            countDownLatch.await();

            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();

            countDownLatch = new CountDownLatch(1);
            cyclicBarrier.await();
        }
    }
}
