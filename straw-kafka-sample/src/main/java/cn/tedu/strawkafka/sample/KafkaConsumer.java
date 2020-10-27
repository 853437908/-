package cn.tedu.strawkafka.sample;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class KafkaConsumer {

    @Autowired
    private Gson gson;

    // 以上注解中的topics参数的值，必须与发出消息时使用的主题字符串保持一致
    @KafkaListener(topics = "TOPIC_SAMPLE")
    public void receive(ConsumerRecord<String,String> record){
        //ConsumerRecord参数对象的value()就是此前发出的消息
          String messageJson=  record.value();
        //将Json字符串还原为对象
        KafukaMessage message=gson.fromJson(messageJson,KafukaMessage.class);
                //打桩输出
        String dateTimeStr = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        System.err.println("[" + dateTimeStr + "] 接收消息：" + messageJson);
        System.err.println("[" + dateTimeStr + "] 转换对象：" + message);
    }

}
