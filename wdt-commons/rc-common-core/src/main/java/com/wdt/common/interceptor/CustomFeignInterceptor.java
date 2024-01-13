package com.wdt.common.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * Feign 组件的拦截器
 */
@Slf4j
public class CustomFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        // TODO 在这里可以实现一些自定义的逻辑，例如：用户认证
        log.info("执行拦截器....");
    }
}