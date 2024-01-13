package com.wdt.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient //打开服务发现
@SpringBootApplication
@MapperScan(basePackages={"com.wdt.system.module.*.mapper"})
public class RCServiceSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(RCServiceSystemApplication.class, args);
    }
}