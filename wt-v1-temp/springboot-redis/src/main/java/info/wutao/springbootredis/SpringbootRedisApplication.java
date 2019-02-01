package info.wutao.springbootredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringbootRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRedisApplication.class, args);
    }

}

