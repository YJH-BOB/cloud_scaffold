package com.wdt.websocket.config;

import com.wdt.websocket.security.WebSocketInterceptor;
import com.wdt.websocket.service.DefaultHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketConfigurer, WebSocketMessageBrokerConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new DefaultHandler(), "/defaultHandler/{ID}")
                .setAllowedOrigins("*")
                .addInterceptors(new WebSocketInterceptor());
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/endpoint")
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/user", "/mass", "/alone");
        registry.setUserDestinationPrefix("/user");
    }
}
