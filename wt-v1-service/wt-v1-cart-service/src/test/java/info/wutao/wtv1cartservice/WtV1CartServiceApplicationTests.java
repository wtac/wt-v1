package info.wutao.wtv1cartservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WtV1CartServiceApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
        System.err.println(redisTemplate);
    }

    @Test
    public void redisTest() {
        redisTemplate.opsForValue().set("wutaoz", "");

    }

}

