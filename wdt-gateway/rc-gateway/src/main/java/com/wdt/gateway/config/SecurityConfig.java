package com.wdt.gateway.config;

import com.wdt.gateway.handler.DefaultAuthenticationConverter;
import com.wdt.gateway.handler.DefaultReactiveAuthorizationManager;
import com.wdt.gateway.handler.DefaultServerAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.web.server.WebFilter;

/**
 * 鉴权配置类
 */
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    private DefaultServerAuthenticationEntryPoint defaultServerAuthenticationEntryPoint() {
        return new DefaultServerAuthenticationEntryPoint();
    }

    @Bean
    SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        // 需要权限访问的接口,
        http.authorizeExchange(auth -> {
                    auth.pathMatchers("/admin/**", "user/**", "role/**").access(new DefaultReactiveAuthorizationManager("admin"))
                            .pathMatchers("menu/**").access(new DefaultReactiveAuthorizationManager("user"))
                            // 其余有token就能访问
                            .anyExchange().permitAll();
                })
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec.authenticationEntryPoint(defaultServerAuthenticationEntryPoint()))
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable);


        SecurityWebFilterChain chain = http.build();
        for (WebFilter f : chain.getWebFilters().toIterable()) {
            if (f instanceof AuthenticationWebFilter webFilter) {
                //将自定义的AuthenticationConverter添加到过滤器中
                webFilter.setServerAuthenticationConverter(new DefaultAuthenticationConverter());
            }
        }
        return chain;

    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        return new ReactiveAuthenticationManagerAdapter((authentication) -> {
            if (authentication instanceof UsernamePasswordAuthenticationToken gmAccountAuthentication) {
                if (gmAccountAuthentication.getPrincipal() != null) {
                    authentication.setAuthenticated(true);
                    return authentication;
                } else {
                    return authentication;
                }
            } else {
                return authentication;
            }
        });
    }


}
