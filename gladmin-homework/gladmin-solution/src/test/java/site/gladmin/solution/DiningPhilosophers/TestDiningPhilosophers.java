package site.gladmin.solution.DiningPhilosophers;

import java.util.concurrent.Semaphore;

public class TestDiningPhilosophers {//初始化为0, 二进制表示则为00000, 说明当前所有叉子都未被使用
    private volatile int fork = 0;
    //限制 最多只有4个哲学家去持有叉子
    private Semaphore eatLimit = new Semaphore(4);



    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {



        int leftFork = (philosopher + 1) % 5;    //左边的叉子 的编号
        int rightFork = philosopher;    //右边的叉子 的编号
        int leftMask = 1 << leftFork, rightMask = 1 << rightFork;

        eatLimit.acquire();    //限制的人数 -1

        while ((fork & leftMask) != 0) Thread.sleep(1);
        fork ^= leftMask;  //拿起左边的叉子

        while ((fork & rightMask) != 0) Thread.sleep(1);
        fork ^= rightMask; //拿起右边的叉子

        pickLeftFork.run();    //拿起左边的叉子 的具体执行
        pickRightFork.run();    //拿起右边的叉子 的具体执行

        eat.run();    //吃意大利面 的具体执行

        putLeftFork.run();    //放下左边的叉子 的具体执行
        putRightFork.run();    //放下右边的叉子 的具体执行

        fork ^= leftMask; //放下左边的叉子
        fork ^= rightMask;    //放下右边的叉子

        eatLimit.release(); //限制的人数 +1
    }



}