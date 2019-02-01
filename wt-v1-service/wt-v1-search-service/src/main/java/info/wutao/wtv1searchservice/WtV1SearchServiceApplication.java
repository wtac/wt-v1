package info.wutao.wtv1searchservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("info.wutao.v1.mapper")
public class WtV1SearchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WtV1SearchServiceApplication.class, args);
    }

}

