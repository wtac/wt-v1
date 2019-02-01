package info.wutao.wtv1userservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableDubbo
@MapperScan("info.wutao.v1.mapper")
public class WtV1UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WtV1UserServiceApplication.class, args);
    }

}

