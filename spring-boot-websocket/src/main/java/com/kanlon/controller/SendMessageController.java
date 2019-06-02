package com.kanlon.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanlon.response.CommonResponse;
import com.kanlon.service.MessagePubSubService;
import com.kanlon.service.WebSocketHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

/**
 * 发送websocket消息和redis发布订阅模式，作为发布方的controller
 *
 * @author zhangcanlong
 * @since 2019/5/22 9:17
 **/
@RestController
@RequestMapping("/sendMessage")
public class SendMessageController {

    @Autowired
    private WebSocketHandleService webSocketHandleService;

    @Autowired
    private MessagePubSubService messagePubSubService;
    private final static ObjectMapper MAPPER = new ObjectMapper();

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
        boolean b = webSocketHandleService.sendMessageToUser(username, new TextMessage(returnMessage));
        return b ? returnMessage : "该websocket用户不存在";
    }

    /**
     * 向redis发布消息
     *
     * @param username 发布的用户名
     * @param message  发布的消息
     * @return com.kanlon.response.CommonResponse
     **/
    @GetMapping("/redis/push-message")
    public CommonResponse puhSubMessage(@RequestParam("username") String username,
            @RequestParam("message") String message) {
        messagePubSubService.publish(username, message);
        boolean flag;
        try {
            flag = webSocketHandleService.sendMessageToUser(username,
                    new TextMessage(MAPPER.writeValueAsString(CommonResponse.succeedResult(message))));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("json格式对象出错!", e);
        }
        if (flag) {
            return CommonResponse.succeedResult("发送给webSocket成功，发布的消息为：" + username + ":" + message);
        }
        return CommonResponse.failedResult("发送给webSocket失败，发布的消息为：" + username + ":" + message);
    }

}
