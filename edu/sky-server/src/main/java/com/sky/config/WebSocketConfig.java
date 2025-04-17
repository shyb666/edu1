package com.sky.config;


import com.sky.handler.MyWsHandler;
import com.sky.interceptor.MyWsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
@Resource
MyWsHandler myWsHandler;
@Resource
MyWsInterceptor myWsInterceptor;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myWsHandler,"/myWs1")
                .setAllowedOrigins("*").addInterceptors(myWsInterceptor);
    }
}