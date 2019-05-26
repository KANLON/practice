package com.kanlon.controller;

import com.kanlon.handler.MyWebSocketHandle;
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
    private MyWebSocketHandle myWebSocketHandle;

    /**
     * 发送消息给某个websocket客户端
     *
     * @param username 标记websocket登录的用户名
     * @param message  回调的消息
     * @return java.lang.String 返回给websocket的信息
     **/
    @GetMapping("/hello")
    public String sendMessage(@RequestParam("username") String username, @RequestParam("message") String message) {
        String returnMessage = "服务器返回的消息" + username + message;
        boolean b = myWebSocketHandle.sendMessageToUser(username, new TextMessage(returnMessage));
        return b ? returnMessage : "该websocket用户不存在";
    }
}
