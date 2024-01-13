package com.wdt.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients    //feign扫描
@EnableDiscoveryClient //打开服务发现
@SpringBootApplication
public class RCSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(RCSecurityApplication.class, args);
    }
}