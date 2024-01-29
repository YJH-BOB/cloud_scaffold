package com.wdt.consume.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TopicConsumerListener {
    @JmsListener(destination = "${spring.activemq.topic-name}", containerFactory = "topicListener")
    public void readActiveQueue(String message) {
        log.info("topic接受到：{}",message);
    }

}