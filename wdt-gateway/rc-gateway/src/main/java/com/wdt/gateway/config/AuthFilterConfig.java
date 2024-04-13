package com.wdt.gateway.config;

import com.alibaba.fastjson.JSON;
import com.wdt.common.enmus.CodeEnum;
import com.wdt.common.model.Result;
import com.wdt.common.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class AuthFilterConfig {
    private static final String AUTH_TOKEN_URL = "/api-security/login";
    public static final String USER_NAME = "username";
    public static final String FROM_SOURCE = "from-source";

    @Bean
    @Order(0)
    public GlobalFilter jwtAuthGlobalFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest serverHttpRequest = exchange.getRequest();
            ServerHttpResponse serverHttpResponse = exchange.getResponse();
            ServerHttpRequest.Builder mutate = serverHttpRequest.mutate();
            String requestUrl = serverHttpRequest.getURI().getPath();

            if(AUTH_TOKEN_URL.equals(requestUrl)) {
                log.info("登录url，放行");
                return chain.filter(exchange);
            }

            // 从 HTTP 请求头中获取 JWT 令牌
            String token = getToken(serverHttpRequest);
            if (StringUtils.isEmpty(token)) {
                return unauthorizedResponse(exchange, serverHttpResponse, CodeEnum.TOKEN_MISSION);
            }

            // 对Token解签名，并验证Token是否过期
            boolean isJwtNotValid = JWTUtil.isTokenExpired(token);
            if(isJwtNotValid){
                return unauthorizedResponse(exchange, serverHttpResponse, CodeEnum.TOKEN_INVALID);
            }
            // 验证 token 里面的 userId 是否为空
            String username = JWTUtil.getUsernameFromToken(token);
            if (StringUtils.isEmpty(username)) {
                return unauthorizedResponse(exchange, serverHttpResponse, CodeEnum.TOKEN_INVALID);
            }
            // 设置用户信息到请求
//            addHeader(mutate, USER_ID, userId);
            addHeader(mutate, username);
            // 内部请求来源参数清除
            removeHeader(mutate, FROM_SOURCE);
            return chain.filter(exchange.mutate().request(mutate.build()).build());
        };
    }

    //添加头部信息
    private void addHeader(ServerHttpRequest.Builder mutate, Object value) {
        if (value == null) {
            return;
        }
        String valueStr = value.toString();
        String valueEncode = urlEncode(valueStr);
        mutate.header(AuthFilterConfig.USER_NAME, valueEncode);
    }
    //移除头部信息
    private void removeHeader(ServerHttpRequest.Builder mutate, String name) {
        mutate.headers(httpHeaders -> httpHeaders.remove(name)).build();
    }

     //内容编码，配置为UTF-8
     static String urlEncode(String str) {
         return URLEncoder.encode(str, StandardCharsets.UTF_8);
     }

    //请求token
    private String getToken(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst("token");
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith("AUTH")) {
            token = token.replaceFirst("AUTH", StringUtils.EMPTY);
        }
        return token;
    }

    //jwt鉴权失败处理类
    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, ServerHttpResponse serverHttpResponse, CodeEnum codeEnum) {
        log.warn("token异常处理,请求路径:{}", exchange.getRequest().getPath());
        serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        serverHttpResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        Result<Void> result = Result.failed(codeEnum.getCode(), codeEnum.getMsg());
        DataBuffer dataBuffer = serverHttpResponse.bufferFactory()
                .wrap(JSON.toJSONStringWithDateFormat(result, JSON.DEFFAULT_DATE_FORMAT)
                        .getBytes(StandardCharsets.UTF_8));
        return serverHttpResponse.writeWith(Flux.just(dataBuffer));
    }

}