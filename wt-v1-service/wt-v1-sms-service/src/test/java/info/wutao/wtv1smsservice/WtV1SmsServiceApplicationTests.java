package info.wutao.wtv1smsservice;

import info.wutao.v1.api.sms.ISmsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WtV1SmsServiceApplicationTests {

    @Autowired
    private ISmsService iSmsService;

    @Test
    public void contextLoads() {
        iSmsService.smsSender("13237978737", "5950", "3");
    }

}

