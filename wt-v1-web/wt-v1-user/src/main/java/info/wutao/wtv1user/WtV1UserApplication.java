package info.wutao.wtv1user;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDubbo
public class WtV1UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(WtV1UserApplication.class, args);
    }

}

