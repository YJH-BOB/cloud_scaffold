package com.wdt.websocket;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient //打开服务发现
@SpringBootApplication
public class RCWebsocketApplication {
    // springboot+websocket+sockjs进行消息推送,基于STOMP
    public static void main(String[] args) {
        SpringApplication.run(RCWebsocketApplication.class, args);
    }
}