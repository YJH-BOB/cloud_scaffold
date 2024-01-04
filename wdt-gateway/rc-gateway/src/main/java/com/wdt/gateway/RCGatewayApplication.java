package com.wdt.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient //打开服务发现
@SpringBootApplication
public class RCGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(RCGatewayApplication.class, args);
    }
}