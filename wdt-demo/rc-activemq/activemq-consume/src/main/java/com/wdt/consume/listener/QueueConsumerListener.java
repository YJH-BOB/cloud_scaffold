package com.wdt.consume.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class QueueConsumerListener {
    @JmsListener(destination = "${spring.activemq.queue-name}", containerFactory = "queueListener")
    public void readActiveQueue(String message) {
        log.info("queue接受到：{}",message);
    }
}