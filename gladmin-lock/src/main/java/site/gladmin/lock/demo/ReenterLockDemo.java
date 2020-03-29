package site.gladmin.lock.demo;

class Phone{

    public synchronized void sendSMS()throws Exception{

        System.out.println(Thread.currentThread().getName()+"\t invoked sendSMS");
        sendEmail();
    }

    public synchronized void sendEmail()throws Exception{

        System.out.println(Thread.currentThread().getName()+"\t invoked sendEmail");
    }
}


/**
 * 外层获取了锁，内层自动获取到锁，可重入锁（递归锁）synchronized 和 reenterlock
 */
public class ReenterLockDemo {


    public static void main(String[] args) {


        final Phone phone =new Phone();

        new Thread(()->{
            try{
                phone.sendSMS();
            }
            catch(Exception e){e.printStackTrace();}

        },"t1").start();


        new Thread(()->{
            try{
                phone.sendEmail();
            }
            catch(Exception e){e.printStackTrace();}

        },"t2").start();
    }
}
