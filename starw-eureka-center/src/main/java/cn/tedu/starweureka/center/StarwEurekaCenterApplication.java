package cn.tedu.starweureka.center;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class StarwEurekaCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarwEurekaCenterApplication.class, args);
    }




}
