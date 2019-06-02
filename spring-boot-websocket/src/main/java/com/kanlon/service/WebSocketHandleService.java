package com.kanlon.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanlon.response.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * websocket处理类，处理webSocket的链接,管理，发布和接受消息
 *
 * @author zhangcanlong
 * @since 2019/5/13 9:19
 **/
@Service
public class WebSocketHandleService implements WebSocketHandler {

    private final Logger log = LoggerFactory.getLogger(WebSocketHandleService.class);

    private final static ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 存放在线用户列表
     **/
    private static final Map<String, WebSocketSession> USER_MAP = new ConcurrentHashMap<>();

    /**
     * 建立链接后的操作
     *
     * @param session webscoketSession 状态
     **/
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("成功建立连接");
        String username = session.getUri().toString().split("username=")[1];
        if (username != null) {
            USER_MAP.put(username, session);
            session.sendMessage(new TextMessage(MAPPER.writeValueAsBytes(CommonResponse.succeedResult())));
            log.info(session.toString());
        }
        log.info("当前机器在线人数：" + USER_MAP.size());
    }

    /**
     * 服务器处理接受的消息
     *
     * @param webSocketSession session状态
     * @param webSocketMessage 发送的消息
     **/
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) {
        log.info(webSocketMessage.getPayload().toString());
        // 接受消息后回复客户端
        try {
            sendMessageToUser(webSocketSession.getId(),
                    new TextMessage(MAPPER.writeValueAsString(CommonResponse.succeedResult())));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("返回消息时，解析json异常!", e);
        }
    }

    /**
     * 处理连接出错
     *
     * @param session   websocketSession状态
     * @param throwable 异常
     **/
    @Override
    public void handleTransportError(WebSocketSession session, Throwable throwable) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        log.error("socket连接出错," + throwable.getMessage());
        USER_MAP.remove(getClientId(session));
    }

    /**
     * websocket连接关闭之后的处理
     *
     * @param session websocket session登录信息
     * @param status  webSocket关闭状态
     **/
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info(session.getId() + ",连接已关闭：" + status);
        USER_MAP.remove(getClientId(session));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 发送信息给指定用户
     *
     * @param username 用户名
     * @param message  发送的文本消息内容
     * @return 是否发送成功
     */
    public boolean sendMessageToUser(String username, TextMessage message) {
        if (USER_MAP.get(username) == null) {
            log.warn("用户：" + username + "不存在，不能发送给该用户");
            return false;
        }
        WebSocketSession session = USER_MAP.get(username);
        log.info("sendMessage:" + session);
        if (!session.isOpen()) {
            return false;
        }
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("发送消息给用户错误!", e);
            return false;
        }
        return true;
    }

    /**
     * 广播信息
     *
     * @param message 要发送的文本消息
     * @return 是否发送成功
     */
    public boolean sendMessageToAllUsers(TextMessage message) {
        boolean allSendSuccess = true;
        Set<String> clientIds = USER_MAP.keySet();
        WebSocketSession session;
        for (String clientId : clientIds) {
            try {
                session = USER_MAP.get(clientId);
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                log.error("发送消息给全体用户错误!", e);
                allSendSuccess = false;
            }
        }

        return allSendSuccess;
    }

    /**
     * 获取用户标识
     *
     * @param session
     * @return
     */
    private String getClientId(WebSocketSession session) {
        String username = session.getUri().toString().split("username=")[1];
        return username;
    }

}
