package com.kanlon.service;

import com.kanlon.controller.SendMessageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

/**
 * 定时发送消息的服务类
 *
 * @author zhangcanlong
 * @since 2019/5/22 9:08
 **/
@Service
public class SentMessageService {

    private final Logger logger = LoggerFactory.getLogger(SendMessageController.class);

    @Autowired
    private WebSocketHandleService webSocketHandleService;

    @Autowired
    private MessagePubSubService messagePubSubService;

    /**
     * 定时发送消息给websocket服务客户端
     **/
    //    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        Boolean b = webSocketHandleService.sendMessageToUser("zhangcanlong", new TextMessage("服务器主动推送一条消息ccc"));
        System.out.println(b);
    }

    /**
     * 定时发布消息到redis
     **/
//    @Scheduled(initialDelay = 5000, fixedDelay = 10000)
    private void schedule() {
        logger.info("publish message");
        messagePubSubService.publish("admin", "hey you must go now!");
    }
}
