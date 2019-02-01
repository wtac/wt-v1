package info.wutao.wtv1user;

import info.wutao.wtv1user.common.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WtV1UserApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void contextLoads() {
        Object zj = redisTemplate.opsForValue().get("user_phone_code_15387871404");
        System.err.println(zj);
    }

    @Test
    public void test1() {
        String key = "user_phone_code_15387871404";
        String value = "4367";
        redisTemplate.opsForValue().set(key, value, Config.FAILURE_TIME, TimeUnit.MINUTES);
    }

    @Test
    public void test2 () {
        String key = "user_phone_code_15387871404";
        redisTemplate.delete(key);

    }

    @Test
    public void test3(){
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Object zj = redisTemplate.opsForValue().get("user_phone_code_15387871404");
        }
        long end = System.currentTimeMillis();
        System.err.println(end-start);
    }
}

