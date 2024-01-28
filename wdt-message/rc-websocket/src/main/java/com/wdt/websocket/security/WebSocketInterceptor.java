package com.wdt.websocket.security;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class WebSocketInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(@Nullable ServerHttpRequest request, @Nullable ServerHttpResponse serverHttpResponse, @Nullable WebSocketHandler webSocketHandler, @Nullable Map<String, Object> map) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            String ID = request.getURI().toString().split("ID=")[1];
            assert map != null;
            map.put("WEBSOCKET_USERID",ID);
        }
        return true;
    }

    @Override
    public void afterHandshake(@Nullable ServerHttpRequest serverHttpRequest, @Nullable ServerHttpResponse serverHttpResponse,@Nullable  WebSocketHandler webSocketHandler, Exception e) {
        System.out.println("进来webSocket的afterHandshake拦截器！");
    }
}
