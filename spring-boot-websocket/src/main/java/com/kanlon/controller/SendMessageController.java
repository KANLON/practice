package com.kanlon.controller;

import com.kanlon.handler.MyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

/**
 * 发送websocket消息的controller
 *
 * @author zhangcanlong
 * @since 2019/5/22 9:17
 **/
@RestController
@RequestMapping("/sendMessage")
public class SendMessageController {

    @Autowired
    private MyHandler myHandler;

    @GetMapping("/hello")
    public String sendMessage(@RequestParam("username") String username) {
        myHandler.sendMessageToUser(username, new TextMessage("服务器返回的消息"));

        return username;
    }
}
