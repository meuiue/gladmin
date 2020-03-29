package site.gladmin.solution.FooBar;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;

public class TestFooBar {

    @Test
    public void testFooBarPrint() throws BrokenBarrierException, InterruptedException {

        FooBar fooBar= new FooBar(100);

        new Thread(()->{
            while (true){
                try {
                    fooBar.foo(new PrintFoo());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        },"T1").start();

        new Thread(()->{
            while (true) {
                try {
                    fooBar.bar(new PrintBar());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }

        },"T2").start();

    }

}
