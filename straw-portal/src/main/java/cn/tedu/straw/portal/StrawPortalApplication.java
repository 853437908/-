package cn.tedu.straw.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@MapperScan("cn.tedu.straw.portal.mapper")
@SpringBootApplication
public class StrawPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(StrawPortalApplication.class, args);
    }

    @Bean
    public MultipartConfigElement MultipartConfigElement(){
        MultipartConfigFactory factory=new MultipartConfigFactory();
        factory.setMaxRequestSize(DataSize.ofMegabytes(500));
        factory.setMaxRequestSize(DataSize.ofMegabytes(500));
        return factory.createMultipartConfig();

    }



}
