package cn.tedu.straw.reids.tag;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("cn.tedu.straw.reids.tag.mapper")
@SpringBootApplication
public class StrawReidsTagApplication {

    public static void main(String[] args) {
        SpringApplication.run(StrawReidsTagApplication.class, args);
    }

}
