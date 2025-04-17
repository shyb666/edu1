package com.sky.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * @author 肖某
 */
@Component
@Slf4j
public class MyWsInterceptor extends HttpSessionHandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // 你的逻辑代码
        log.info(request.getRemoteAddress().toString()+"开始握手");
        return super.beforeHandshake(request,response,wsHandler,attributes); // 返回true表示继续握手，返回false表示中止握手
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // 你的逻辑代码
        log.info(request.getRemoteAddress().toString()+"完成握手");
        super.afterHandshake(request,response,wsHandler,exception);
    }
}
