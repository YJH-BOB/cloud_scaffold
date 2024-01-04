package com.central;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zlt
 * @date 2019/10/5
 * <p>
 * Blog: http://zlt2000.gitee.io
 * Github: https://github.com/zlt2000
 */

@EnableDiscoveryClient //打开服务发现
@SpringBootApplication
public class RCGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(RCGatewayApplication.class, args);
    }
}