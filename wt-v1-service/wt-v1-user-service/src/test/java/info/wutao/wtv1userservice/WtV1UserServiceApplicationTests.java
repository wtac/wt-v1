package info.wutao.wtv1userservice;

import info.wutao.v1.api.User.IUserService;
import info.wutao.v1.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WtV1UserServiceApplicationTests {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
    }

    @Test
    public void getByIdTest(){
//        System.out.println(userService.selectByPrimaryKey(1L));
        User user = new User();

        user.setUsername("root");
        user.setFlag((byte) 4);
        user.setPassword("root");
        user.setUuid("111111111111");
        user.setEmail("mrwutao.");
//        userService.activateUser(user);
        System.out.println(userService.insertSelective(user));
        System.out.println(user.getId());
    }

    @Test
    public void redisTest () {
        redisTemplate.opsForValue().set("wutaoddd", "eeee");
    }

}

