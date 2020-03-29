package site.gladmin.lock.demo;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 利用循环的方式尝试获取锁，反复的循环很消耗资源。但也减少了线程上下文切换的消耗。
 */
public class SpinLockDemo {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock(){

        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+"\t come in ");

        while (!atomicReference.compareAndSet(null,thread)){}

    }

    public void myUnLock(){

        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+"\t invoker myUnLock");

    }

    public static void main(String[] args) {

        SpinLockDemo spinLockDemo = new SpinLockDemo();


        new Thread(()->{
            spinLockDemo.myLock();
            try{TimeUnit.SECONDS.sleep(500);}catch (InterruptedException e){e.printStackTrace();}
            spinLockDemo.myUnLock();

            },"AA").start();

        try{TimeUnit.SECONDS.sleep(1);}catch (InterruptedException e){e.printStackTrace();}


        new Thread(()->{

            spinLockDemo.myLock();
            try{TimeUnit.SECONDS.sleep(1);}catch (InterruptedException e){e.printStackTrace();}
            spinLockDemo.myUnLock();

        },"BB").start();
    }
}
