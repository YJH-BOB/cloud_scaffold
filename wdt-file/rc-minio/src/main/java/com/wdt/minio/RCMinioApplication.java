package com.wdt.minio;

import com.wdt.common.config.FeignClientConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@Import({FeignClientConfiguration.class})
@EnableDiscoveryClient //打开服务发现
@SpringBootApplication
public class RCMinioApplication {
    public static void main(String[] args) {
        SpringApplication.run(RCMinioApplication.class, args);
    }
}