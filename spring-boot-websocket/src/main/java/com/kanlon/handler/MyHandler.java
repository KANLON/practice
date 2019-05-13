package com.kanlon.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * websocket处理类，相当于controller的处理器
 *
 * @author zhangcanlong
 * @since 2019/5/13 9:19
 **/
public class MyHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,String> map = objectMapper.readValue(payload, HashMap.class);
        System.out.println("=====接受到的数据"+map);
        session.sendMessage(new TextMessage("服务器返回收到的信息," + payload));
    }
}
