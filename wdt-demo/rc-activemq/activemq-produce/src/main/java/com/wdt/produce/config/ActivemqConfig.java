package com.wdt.produce.config;

import jakarta.jms.Queue;
import jakarta.jms.Topic;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ActivemqConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String username;

    @Value("${spring.activemq.topic-name}")
    private String password;

    @Value("${spring.activemq.queue-name}")
    private String queueName;

    @Value("${spring.activemq.topic-name}")
    private String topicName;

    @Bean(name = "queue")
    public Queue queue() {
        return new ActiveMQQueue(queueName);
    }

    @Bean(name = "topic")
    public Topic topic() {
        return new ActiveMQTopic(topicName);
    }
}