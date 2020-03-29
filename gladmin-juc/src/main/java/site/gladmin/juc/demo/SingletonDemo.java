package site.gladmin.juc.demo;

/*
* DCL 多线程下的 解决单例模式
* 1.Double Check Lock 双端检锁机制
* 2.禁止指令重排；
*
* */
public class SingletonDemo {

    //如果不加阻止指令重拍，多线程就会出现问题。
    public static volatile SingletonDemo instance = null;

    private  SingletonDemo(){

        System.out.println(Thread.currentThread().getName()+"\t 我是单例方法 SingletonDemo（）");
    }

    //DCL   Double Check Lock 双端检锁机制
    public  static SingletonDemo getInstance(){

        if (instance == null){

            synchronized (SingletonDemo.class){

                if(instance == null){

                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }


    public static void main(String[] args) {

//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());

        for (int i =1; i<=10;i++){
            new Thread(()->{

                SingletonDemo.getInstance();
            },String.valueOf(i)).start();
        }

    }
}
