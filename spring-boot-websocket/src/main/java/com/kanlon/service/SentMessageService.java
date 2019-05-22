package com.kanlon.service;

import com.kanlon.handler.MyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

/**
 * 发送消息的服务类
 *
 * @author zhangcanlong
 * @since 2019/5/22 9:08
 **/
@Service
public class SentMessageService {

    @Autowired
    private MyHandler myHandler;

    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        Boolean b = myHandler.sendMessageToUser("888", new TextMessage("服务器主动推送一条消息ccc"));
        System.out.println(b);
    }
}
