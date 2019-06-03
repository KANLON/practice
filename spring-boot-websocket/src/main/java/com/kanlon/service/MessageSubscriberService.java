package com.kanlon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanlon.model.SimpleMessage;
import com.kanlon.response.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

/**
 * 消息订阅类
 *
 * @author zhangcanlong
 * @since 2019-06-02
 **/
@Service
public class MessageSubscriberService {

    private final Logger logger = LoggerFactory.getLogger(MessageSubscriberService.class);

    private final static ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private WebSocketHandleService webSocketHandleService;

    /**
     * 处理订阅的消息
     *
     * @param message 订阅的消息
     * @param pattern 订阅的模式
     **/
    public void onMessage(SimpleMessage message, String pattern) {
        try {
            logger.info("topic {} received {} ", pattern, new ObjectMapper().writeValueAsString(message));
            boolean flag = webSocketHandleService.sendMessageToUser(message.getPublisher(),
                    new TextMessage(MAPPER.writeValueAsString(CommonResponse.succeedResult(message))));
            if (flag) {
                logger.info("发送给webSocket成功!发送的消息是：" + message.toString());
            } else {
                logger.warn("发送给webSocket失败!发送的消息是" + message.toString());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("订阅处理json格式错误!", e);
        }
    }
}
