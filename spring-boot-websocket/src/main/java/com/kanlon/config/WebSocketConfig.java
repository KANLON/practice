package com.kanlon.config;

import com.kanlon.handler.MyWebSocketHandle;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * websocket的配置类，包含跨域处理
 *
 * @author zhangcanlong
 * @since 2019-05-13
 **/
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MyWebSocketHandle(), "myHandler/{username}").setAllowedOrigins("*");
    }


}
