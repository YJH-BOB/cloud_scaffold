package com.wdt.common.config;

import com.wdt.common.interceptor.CustomFeignInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {
    /**
     * 注入自定义的拦截器
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new CustomFeignInterceptor();
    }
}
