package com.kanlon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanlon.model.SimpleMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 消息订阅类
 *
 * @author zhangcanlong
 * @since 2019-06-02
 **/
@Service
public class MessageSubscriber {

    private final Logger logger = LoggerFactory.getLogger(MessageSubscriber.class);

    /**
     * 处理订阅的消息
     *
     * @param message 订阅的消息
     * @param pattern 订阅的模式
     **/
    public void onMessage(SimpleMessage message, String pattern) {
        try {
            logger.info("topic {} received {} ", pattern, new ObjectMapper().writeValueAsString(message));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
