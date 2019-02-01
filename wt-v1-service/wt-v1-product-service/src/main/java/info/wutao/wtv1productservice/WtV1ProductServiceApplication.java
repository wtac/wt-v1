package info.wutao.wtv1productservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;

@SpringBootApplication
@EnableDubbo
@MapperScan("info.wutao.v1.mapper")
public class WtV1ProductServiceApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(WtV1ProductServiceApplication.class, args);
        System.in.read();
    }

}


