package com.wdt.security.config;

import com.wdt.common.factory.GenericFallbackFactory;
import com.wdt.security.feign.RcSystemServiceClient;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    @Bean
    public <T> GenericFallbackFactory<T> fallbackFactory() {
        return new GenericFallbackFactory<>();
    }
}


