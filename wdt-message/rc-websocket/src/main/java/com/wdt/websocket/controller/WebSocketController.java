package com.wdt.websocket.controller;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/heartbeat")
    @SendTo("/topic/heartbeat")
    public void say(){
		template.convertAndSend("/topic/heartbeat", "心跳...." );
    }

    //广播推送消息
    @GetMapping("/topic")
    public void sendTopicMessage(JSONObject jsonObject) {
        log.info("后台广播推送 {}" ,jsonObject );
        System.out.println("后台广播推送！");
        this.template.convertAndSend("/topic/getResponse", jsonObject);
    }

    //一对一推送消息
    @GetMapping("/queue")
    public void sendQueueMessage(JSONObject jsonObject) {
        log.info("后台一对一推送 {}" ,jsonObject );
        Long id = jsonObject.getLong("id");
        this.template.convertAndSendToUser(String.valueOf(id), "/queue/getResponse", jsonObject);
    }
}
