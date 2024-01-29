package com.wdt.produce;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.jms.annotation.EnableJms;


@EnableDiscoveryClient //打开服务发现
@SpringBootApplication
@EnableJms
public class RCActivemqProduceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RCActivemqProduceApplication.class, args);
    }
}