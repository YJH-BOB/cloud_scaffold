package com.wdt.gateway.filter;

import com.alibaba.nacos.shaded.com.google.gson.JsonObject;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/**
 * Description: 权限控制
 * Author: admin
 * Date: 2024/1/3
 */
public class AuthGlobalFilter implements GlobalFilter {

    /**
     * 1.接口放行
     * 2.token校验
     * 3.请求头中添加用户，便于获取用户信息
     * 4.内部接口校验，不允许从网关访问
     */
    // 不走token校验的接口
    private static final Set<String> PASS_URL =  new HashSet<>() ;

    // 不允许web调用的接口
    private static final Set<String> NOPASS_URL =  new HashSet<>() ;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        if(PASS_URL.contains(path)){
            // 创建可变副本，交换请求对象
            request = exchange.getRequest().mutate().header("plublic", "1").build();
            exchange.mutate().request(request);
            return chain.filter(exchange);
        }
        if(NOPASS_URL.contains(path)){
            // 返回一个响应
          return out(exchange.getResponse());
        }




        return null;
    }
    private Mono<Void> out(ServerHttpResponse response) {
        JsonObject message = new JsonObject();
        message.addProperty("success", false);
//        message.addProperty("code", CodeEnum.INTERIOR.);
//        message.addProperty("data", "内部接口,访问失败");
        byte[] bits = message.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }
}
