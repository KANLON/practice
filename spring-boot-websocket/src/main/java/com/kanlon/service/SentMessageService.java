package com.kanlon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

/**
 * 发送消息的服务类，暂时不可用
 *
 * @author zhangcanlong
 * @since 2019/5/22 9:08
 **/
@Service
public class SentMessageService {

    @Autowired
    private WebSocketHandleService webSocketHandleService;

    //    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        Boolean b = webSocketHandleService.sendMessageToUser("zhangcanlong", new TextMessage("服务器主动推送一条消息ccc"));
        System.out.println(b);
    }
}
