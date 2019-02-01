package info.wutao.wtv1sso;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDubbo
public class WtV1SsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WtV1SsoApplication.class, args);
    }

}

