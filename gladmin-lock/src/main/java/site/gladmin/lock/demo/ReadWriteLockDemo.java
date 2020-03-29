package site.gladmin.lock.demo;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁的问题，
 * 操作需要考虑，原子性
 *
 *
 */
class MyCache{

    private volatile Map<String,Object> map = new HashMap<>();
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void put(String key,Object value){

        rwLock.writeLock().lock();
        try{

            System.out.println(Thread.currentThread().getName()+"\t 正在写入:"+key);

            try{TimeUnit.MICROSECONDS.sleep(300);}catch (InterruptedException e){e.printStackTrace();}
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t 写入完成！");
        }
        catch(Exception e){e.printStackTrace();}
        finally {
            rwLock.writeLock().unlock();
        }
    }

    public void get(String key){

        rwLock.readLock().lock();
        try{

            System.out.println(Thread.currentThread().getName()+"\t 正在读取：");
            try{TimeUnit.MICROSECONDS.sleep(300);}catch (InterruptedException e){e.printStackTrace();}
            Object result = map.get(key);

            System.out.println(Thread.currentThread().getName()+"\t 读取完成：" + result);
        }
        catch(Exception e){e.printStackTrace();}
        finally {
            rwLock.readLock().unlock();
        }
    }

}

public class ReadWriteLockDemo {

    public static void main(String[] args) {

        MyCache myCache =new MyCache();
        for (int i = 1; i <= 5; i++) {

            final  int tempInt = i;
            new Thread(() -> {
                myCache.put(tempInt + "", tempInt + "");
            }, String.valueOf(i)).start();
        }

        for (int i = 1; i <= 5; i++) {

            final  int tempInt = i;
            new Thread(() -> {
                myCache.get(tempInt + "");
            }, String.valueOf(i)).start();
        }
    }
}
