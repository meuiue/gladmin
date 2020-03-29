package site.gladmin.juc.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {

    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);

    public static void main(String[] args) {
        System.out.println("=========ABA问题");

        new Thread(()->{

            atomicReference.compareAndSet(100,101);
            atomicReference.compareAndSet(101,100);

        },"t1").start();

        new Thread(()->{

            try{TimeUnit.SECONDS.sleep(1);}catch (InterruptedException e){e.printStackTrace();}
            System.out.println(atomicReference.compareAndSet(100,2019)+"\t"+atomicReference.get());

        },"t2").start();

        try{TimeUnit.SECONDS.sleep(2);}catch (InterruptedException e){e.printStackTrace();}
        System.out.println("=============解决ABA问题");


        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第一次的版本号："+stamp);

            try{TimeUnit.SECONDS.sleep(1);}catch (InterruptedException e){e.printStackTrace();}


            // 做一次ABA操作
            atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t第2次的版本号："+atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            System.out.println(Thread.currentThread().getName()+"\t第3次的版本号："+atomicStampedReference.getStamp());

        },"t3").start();

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()+"\t第一次的版本号："+stamp);

            try{TimeUnit.SECONDS.sleep(3);}catch (InterruptedException e){e.printStackTrace();}
            boolean finish = atomicStampedReference.compareAndSet(100,2020,stamp,atomicStampedReference.getStamp()+1);

            System.out.println("线程T4修改结果："+finish+"\t版本号："+atomicStampedReference.getStamp()+"\t结果值："+atomicStampedReference.getReference());

        },"t4").start();

    }
}
