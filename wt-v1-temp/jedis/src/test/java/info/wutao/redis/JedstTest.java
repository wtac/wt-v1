package info.wutao.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

public class JedstTest {

    /**
     * 操作value类型
     */
    @Test
    public void jdestHello() {
        Jedis jedis = new Jedis("192.168.2.2", 6379);
        jedis.auth("wutao");

//        jedis.set("wutao", "嘿嘿嘿");
//
//        String info = jedis.get("wutao");
//        System.out.println(info);
//
//        System.out.println("================");
//
//        Long row = jedis.del("wutao");
//
//        System.out.println(row);
//
//        System.out.println(jedis.get("wutao"));

//        Long age = jedis.incr("age");
//
//        System.out.println(age);
//
//        age = jedis.incrBy("age", 100);
//
//        System.out.println(age);

        jedis.set("邬涛123", "21");
        System.out.println(jedis.get("邬涛123"));
    }

    /**
     * 操作hash类型
     */
    public void hashTest () {

    }

    /**
     * 操作集合
     */
    @Test
    public void listTest() {
        Jedis jedis = new Jedis("192.168.2.2", 6379);
        jedis.auth("wutao");
        System.out.println(jedis.get("邬涛123"));

        Long sadd = jedis.sadd("法鸡", "欧文", "须", "一法师的发");
        Set<String> smembers = jedis.smembers("法鸡");
        System.out.println(smembers);
    }
}
