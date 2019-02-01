package info.wutao.wtv1emailservice;

import info.wutao.v1.api.email.IEmailService;
import info.wutao.v1.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WtV1EmailServiceApplicationTests {

    @Autowired
    private IEmailService emailService;

    @Test
    public void contextLoads() {
        User user = new User();
        user.setEmail("mrwutao@foxmail.com");
        System.out.println(emailService.sendActivateEmail("mrwutao@foxmail.com", "激活邮件", "www.baidu.com", user));
    }

}

