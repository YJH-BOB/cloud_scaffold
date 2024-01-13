package com.wdt.security;

import com.wdt.common.config.FeignClientConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@EnableFeignClients    //feign扫描
@EnableDiscoveryClient //打开服务发现
@SpringBootApplication
@Import({FeignClientConfiguration.class})
public class RCSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(RCSecurityApplication.class, args);
    }
}