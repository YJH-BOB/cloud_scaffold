package com.wdt.websocket.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class DefaultHandler implements WebSocketHandler {

    private final Map<String, WebSocketSession> users = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("成功建立连接");
        String ID = Objects.requireNonNull(session.getUri()).toString().split("ID=")[1];
        if (ID != null) {
            users.put(ID, session);
            session.sendMessage(new TextMessage("成功建立socket连接"));
            log.info(ID);
            log.info(session.toString());
        }
        log.info("当前在线人数：" + users.size());
    }

    @Override
    public void handleMessage(@Nullable WebSocketSession session, @Nullable WebSocketMessage<?> webSocketMessage)  {
        try {
            assert webSocketMessage != null;
            JSONObject jsonObject = JSON.parseObject(webSocketMessage.getPayload().toString());
            log.info(jsonObject.getString("id"));
            assert session != null;
            log.info(jsonObject.getString("message") + ":来自" + session.getAttributes().get("WEBSOCKET_USERID") + "的消息");
            sendMessageToUser(jsonObject.getString("id"), new TextMessage("服务器收到了，hello!"));
        } catch (Exception e) {
            log.error("处理消息时发生异常", e);
        }
    }

    public void sendMessageToUser(String clientId, TextMessage message) {
        WebSocketSession session = users.get(clientId);
        if (session == null || !session.isOpen()) {
            return;
        }

        try {
            session.sendMessage(message);
            log.info("sendMessage: {}", session);
        } catch (IOException e) {
            log.error("向用户发送消息时发生异常", e);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, @Nullable Throwable exception) {
        if (session.isOpen()) {
            try {
                session.close();
            } catch (IOException e) {
                log.error("关闭会话时发生异常", e);
            }
        }
        log.error("连接出错", exception);
        users.remove(getClientId(session));
    }

    @Override
    public void afterConnectionClosed(@Nullable WebSocketSession session, @Nullable CloseStatus status)  {
        log.info("连接已关闭：" + status);
        users.remove(getClientId(session));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private String getClientId(WebSocketSession session) {
        try {
            return session.getAttributes().get("WEBSOCKET_USERID").toString();
        } catch (Exception e) {
            return null;
        }
    }
}
