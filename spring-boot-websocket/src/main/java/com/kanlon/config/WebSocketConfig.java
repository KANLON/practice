package com.kanlon.config;

import com.kanlon.handler.MyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
/**
 * websocket的跨域处理
 * @author zhangcanlong
 * @since 2019-05-13
 **/
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "myHandler/{ID}").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new MyHandler();
    }

}
