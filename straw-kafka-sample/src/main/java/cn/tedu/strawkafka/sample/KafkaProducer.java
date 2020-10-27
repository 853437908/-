package cn.tedu.strawkafka.sample;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.channels.SeekableByteChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class KafkaProducer {

    @Autowired
    private Gson gson;
    @Autowired(required = false)
    private KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(initialDelay = 0, fixedRate = 5 * 1000)
    public void sendMessage() {
        // 准备需要发送的“消息”
        KafukaMessage message = new KafukaMessage();
        message.setId(1);
        message.setMessage("这是一条由Kafka处理的消息");
        message.setTime(System.currentTimeMillis());
        // 将“消息”转换为JSON字符串
        String messageJson = gson.toJson(message);
        // 执行发送JSON格式的“消息”
        kafkaTemplate.send("TOPIC_SAMPLE", messageJson);
        // 打桩输出
        String dateTimeStr = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
        System.err.println("[" + dateTimeStr + "] 发出消息：" + messageJson);
    }

}
