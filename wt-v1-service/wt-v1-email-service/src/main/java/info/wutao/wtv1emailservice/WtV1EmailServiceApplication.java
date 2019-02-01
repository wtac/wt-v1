package info.wutao.wtv1emailservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDubbo
public class WtV1EmailServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WtV1EmailServiceApplication.class, args);
    }

}

