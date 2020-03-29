package site.gladmin.thread.demo;


import static java.lang.Thread.currentThread;
import static java.lang.Thread.interrupted;

/**
 * 线程终端Demo
 *
 * boolean interrupted() & void interrupt() & boolean isInterrupted()
 *
 */

public class ThreadInterruptDemo {


    public static void main(String[] args) {



        new Thread(()->{

            ThreadRun();

        },"T1").start();



        new Thread(()->{

            ThreadRun();
        },"T2").start();




    }




    private static void ThreadRun() {
        for (int i = 0; i < 10000; i++) {

        }
        System.out.println("4"+currentThread().getName()+interrupted());
        System.out.println("5"+currentThread().getName()+interrupted());
    }

}
