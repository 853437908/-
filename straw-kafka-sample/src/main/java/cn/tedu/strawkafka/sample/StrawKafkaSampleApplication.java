package cn.tedu.strawkafka.sample;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableKafka
@SpringBootApplication
@EnableScheduling
public class StrawKafkaSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(StrawKafkaSampleApplication.class, args);
    }

    @Bean
    public Gson gson(){
        return new Gson();
    }



}
