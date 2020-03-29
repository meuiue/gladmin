package site.gladmin.redis.demo;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.*;

/**
 * 测试Jedis的用法
 *
 *
 */
public class TestJedis {



    @Test
    public void testJedis01(){

        Jedis jedis = new Jedis("127.0.0.1",6379);

        System.out.println(jedis.ping());

    }

    @Test
    public void testAPI(){

        Jedis jedis = new Jedis("127.0.0.1",6379);

        jedis.set("k1","v1");
        jedis.set("k2","v1");
        jedis.set("k3","v1");

        System.out.println(jedis.get("k1"));

        Set<String> keys = jedis.keys("*");
        System.out.println(keys.size());


         for (Iterator iterator= keys.iterator();iterator.hasNext();){

             String key = (String) iterator.next();
             System.out.println(key);
         }
         System.out.println("jedis.exists:"+jedis.exists("k2"));
         System.out.println(jedis.ttl("k1"));

         jedis.set("k4","ke-redis");

        System.out.println("-------------------");
        jedis.mset("str1","v1","str2","v2");
        System.out.println(jedis.mget("str1","str2"));

        //list

        System.out.println("-------------------");
        List<String> list = jedis.lrange("mylist",0,-1);
        for (String item:list){
            System.out.println(item);
        }
        System.out.println("-------------------");
        //set
        jedis.sadd("orders","jd001");
        jedis.sadd("orders","jd002");
        jedis.sadd("orders","jd003");
        Set<String> set = jedis.smembers("orders");
        for (Iterator iterator = set.iterator(); iterator.hasNext();){
            String string = (String) iterator.next();
            System.out.println(string);
        }

        jedis.srem("orders","jd002");
        System.out.println(jedis.smembers("orders").size());
        System.out.println("-------------------");
        //hash
        jedis.hset("hash1","username","list");
        System.out.println(jedis.hget("hash1","username"));
        Map<String,String> map = new HashMap<String, String>();

        map.put("telphone","12312141324");
        map.put("address","fwiee");
        map.put("email","gladmin@qqq.com");

        jedis.hmset("hash2",map);
        List<String> result = jedis.hmget("hash2","telphone","email");
        for (String element:result){
            System.out.println(element);
        }

        //zset



    }


    @Test
    public  void  testTx(){
        Jedis jedis = new Jedis("127.0.0.1",6379);

        Transaction transaction = jedis.multi();

        transaction.set("k4","v44");
        transaction.set("k5","v5");
        transaction.set("k6","v6");

        transaction.exec();

//        transaction.discard();

        System.out.println(jedis.get("k4"));
    }

}
