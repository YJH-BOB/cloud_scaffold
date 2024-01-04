package com.wdt.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

/**
 * 跨域请求配置
 */
@Configuration
public class CorsConfig {

    private static final String ORIGIN_PATTERN = "*";
    @Value("${cors.urlWhite:*}")
    private String urlWhitelist;

    @Bean
    public CorsWebFilter corsWebFilter() {
        if (urlWhitelist.contains(ORIGIN_PATTERN)) {
            return setCorsWebFilter(new String[]{"*"});
        }
        return setCorsWebFilter(urlWhitelist.split(","));
    }

    private CorsWebFilter setCorsWebFilter(String[] urls) {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        for (String url : urls) {
            corsConfiguration.addAllowedOriginPattern(url);
        }
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsWebFilter(source);
    }

}
