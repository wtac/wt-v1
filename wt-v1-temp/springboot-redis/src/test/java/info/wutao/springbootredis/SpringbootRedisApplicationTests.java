package info.wutao.springbootredis;

import info.wutao.v1.base.ThreadPoolService;
import info.wutao.v1.entity.ProductDesc;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootRedisApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
        System.err.println(redisTemplate.opsForValue().get("邬涛12"));
    }

    /**
     * 从数据库中查询数据后放入redis缓存中
     * 先从redis中查询，如果不存在，则从数据库中查询，然后放入redis中
     * 问题：多线程中有问题
     */
    @Test
    public void test1 () {
        //查询redis
        ProductDesc productDesc = (ProductDesc) redisTemplate.opsForValue().get("productDesc:" + 1);

        if (productDesc == null) {

            //redis中没有，查询数据库
            productDesc = new ProductDesc(1L, 1L, "1");

            //放入redis中
            redisTemplate.opsForValue().set("productDesc:" + 1, productDesc);

            System.err.println("redis中不存在，查询数据库============"+productDesc.toString());
        } else {

            //redis中存在
            System.err.println("查询redis========="+productDesc.toString());
        }
    }

    /**
     * test1的方式，当高并发的时候，会带来一个问题，
     * 线程a判断取出的为null,从数据库中查询，但是还没有放入redis中，线程b开始，发现redis为null
     * 线程b也进入数据库中查询，这样就会造成多次查询
     */
    @Test
    public void test2 () {
        ThreadPoolService threadPool = new ThreadPoolService(8, 20, 100, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());

        for (int i = 0; i < 100; i++) {
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    test1();
                }
            });
        }

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分布式锁
     */
    @Test
    public void test3 () throws IOException {

        //查询redis
        ProductDesc productDesc = (ProductDesc) redisTemplate.opsForValue().get("productDesc:" + 1);

        if (productDesc == null) {

            //获取分布式锁
            String lockValue = UUID.randomUUID().toString();
            Boolean ifAbsent = redisTemplate.opsForValue().setIfAbsent("productDesc:locks", lockValue);

            try {

                if (ifAbsent) {

                    //redis中没有，查询数据库
                    productDesc = new ProductDesc(1L, 1L, "1");

                    //放入redis中
                    redisTemplate.opsForValue().set("productDesc:" + 1, productDesc);

                    System.err.println("查询数据库======="+productDesc.toString());

                } else {
                    try {
                        Thread.sleep(10);
                        test3();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            } finally {
                Object currntValue = redisTemplate.opsForValue().get("productDesc:locks");
                if (lockValue.equals(currntValue)) {

                    redisTemplate.delete("productDesc:locks");
                }
            }

        } else {

            //redis中存在
            System.err.println("查询redis========="+productDesc.toString());
        }
    }

    @Test
    public void test4 () {

        ThreadPoolService threadPool = new ThreadPoolService(4, 8, 100, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(100));

        for (int i = 0; i < 100; i++) {
            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        test3();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}

