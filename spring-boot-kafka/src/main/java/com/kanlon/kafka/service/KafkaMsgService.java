package com.kanlon.kafka.service;

import com.kanlon.kafka.response.CommonResponse;
import com.kanlon.kafka.util.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

/**
 * kafka的数据接受和发送的serice
 *
 * @author zhangcanlong
 * @since 2020/10/19 22:09
 **/
@Service
@Slf4j
public class KafkaMsgService {

    @Value("${spring.kafka.topic.topic1}")
    private String topicValue;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    /**
     * 测试发送kafka消息
     */
    public void sendKafkaMsg(String msg) {
        // 通过 消息队列通知，集群去更新数据集信息
        String needSendMsg = JacksonUtils.obj2json(new CommonResponse<String>(1, msg), true);
        log.info("kafka发送消息到其他机器。发送的信息为：[{}]", needSendMsg);
        this.kafkaTemplate.send(this.topicValue, needSendMsg);
    }


    /**
     * 接受kafka消息，使用随机数作为消费者组名，让每个机器可以接受到数据。
     * 同时注意，这里的方法一定要为非private,否则在该方法中会获取不到的注入的类或者配置信息
     */
    @KafkaListener(topics = "${spring.kafka.topic.topic1}", groupId = "'dashboard'+${random.value}")
    public String receiveKafkaMsgFromKafka(String content, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) {
        log.info("接受的kafka消息为:{},topic为：{},时间为：{}", content, topic, ts);
        return content;
    }
}
