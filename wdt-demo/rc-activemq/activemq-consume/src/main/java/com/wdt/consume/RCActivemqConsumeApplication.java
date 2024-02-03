package com.wdt.consume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.jms.annotation.EnableJms;


@EnableDiscoveryClient //打开服务发现
@SpringBootApplication
@EnableJms
public class RCActivemqConsumeApplication {
    public static void main(String[] args) {
        SpringApplication.run(RCActivemqConsumeApplication.class, args);
    }
}