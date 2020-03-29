package site.gladmin.juc.demo;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class TestData{
    //测试主线程不能访问到number的情况，没人通知给main线程；
    //    int number =0;

    //main线程可以看到其他线程里的值,其他线程得到最新通知；
    volatile int number =0;

    public void addnumber() {
        this.number = 60;
    }

    //synchronized
    public  void  addPlusPlus(){

        this.number++;
    }


    //验证 原子性
    AtomicInteger atomicInteger = new AtomicInteger();
    public void addAtomic(){

        atomicInteger.getAndIncrement();
    }
}


/*  验证volatile
* 1.验证volatile可见性；
* 2.验证volatile不能原子性；
* 3.atomic的验证，保证原子性；
* */
public class VolatileDemo {


    public static void main(String[] args) {

        //showok();


        TestData testData = new TestData();

        for (int i =1; i<=20;i++){
            new Thread(()->{

                for (int j =1 ;j<=1000; j++){
//                    testData.addPlusPlus();
                    testData.addAtomic();

                }


            },String.valueOf(i)).start();
        }

        while (Thread.activeCount()>2) {

            Thread.yield();
        }

        //应该输入2万，但是因为没有原子性，就看不到2万的结果
        //用 synchronized 可以解决得到2万，但不是最好解决方案，消耗资源
        System.out.println(Thread.currentThread().getName()+"\t finally number value:"+testData.number);

        // 用Atomic来保证原子性
        System.out.println(Thread.currentThread().getName()+"\t finally number value:"+testData.atomicInteger);

    }



    //1.验证volatile可见性；
    private static void showok() {
        TestData testData = new TestData();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t come in");

            try{TimeUnit.SECONDS.sleep(3);}catch (InterruptedException e){e.printStackTrace();}

            testData.addnumber();
            System.out.println(Thread.currentThread().getName() +"\t update number");
        },"aaa").start();

        while (testData.number == 0){

        }

        System.out.println(Thread.currentThread().getName() + "\t mission is over");
    }
}
