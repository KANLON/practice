package com.kanlon.kafka.controller;

import com.kanlon.kafka.response.CommonResponse;
import com.kanlon.kafka.service.KafkaMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * kafka消息发送和接受测试
 *
 * @author zhangcanlong
 * @since 2020/10/19 22:02
 **/
@RestController
@RequestMapping("/api/kafka")
public class KafkaMsgController {

    @Autowired
    private KafkaMsgService kafkaMsgService;

    /**
     * 测试发送kafka 数据
     *
     * @param testMsg 测试味精
     * @return {@link CommonResponse<Long>}
     */
    @RequestMapping("/send")
    public CommonResponse<Long> sendKafkaMsg(String testMsg) {
        kafkaMsgService.sendKafkaMsg(testMsg);
        return CommonResponse.succeedResultWithGenerics(null);
    }
}
