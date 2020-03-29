package site.gladmin.lock.demo;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {


    /**
     * 堵塞队列
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {

//        List<String> list = new ArrayList<>();
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        //直接报异常
//        System.out.println(blockingQueue.add("a"));
//        System.out.println(blockingQueue.add("b"));
//        System.out.println(blockingQueue.add("c"));
//
//        System.out.println(blockingQueue.remove());
//
//        System.out.println(blockingQueue.element());

        //返回 特殊值
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        System.out.println(blockingQueue.offer("x"));

        System.out.println(blockingQueue.peek());

        System.out.println(blockingQueue.poll());


        //添加满了就一直等在添加
//        blockingQueue.put("a");
//        blockingQueue.put("b");
//        blockingQueue.put("c");
//        blockingQueue.put("x");
//
//        blockingQueue.take();

        //定时添加
//        System.out.println(blockingQueue.offer("a",2L,TimeUnit.SECONDS));
//        System.out.println(blockingQueue.poll(2L,TimeUnit.SECONDS));

    }
}
