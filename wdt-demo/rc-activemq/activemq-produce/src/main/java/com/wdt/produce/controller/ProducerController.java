package com.wdt.produce.controller;

import com.wdt.common.model.Result;
import jakarta.jms.Destination;
import jakarta.jms.Queue;
import jakarta.jms.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ProducerController {
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @Autowired
    private Topic topic;

    @PostMapping("/queue/send")
    public Result<Object> sendQueue(@RequestBody String msg) {
        this.sendMessage(this.queue, msg);
        return Result.succeed("success");
    }

    @PostMapping("/topic/send")
    public Result<Object> sendTopic(@RequestBody String msg) {
        this.sendMessage(this.topic, msg);
        return Result.succeed("success");
    }

    private void sendMessage(Destination destination, final String message) {
        jmsMessagingTemplate.convertAndSend(destination, message);
    }
}