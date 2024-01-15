package com.wdt.gateway.handler;


import com.alibaba.fastjson.JSONObject;
import com.wdt.common.enmus.CodeEnum;
import com.wdt.common.model.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 鉴权统一异常处理
 */

public class DefaultServerAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {


    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        System.out.println("无权限访问");
        Result<Object> data = Result.failed(CodeEnum.UNAUTHORIZE.code, CodeEnum.UNAUTHORIZE.getMsg());
        return Mono.error(new  AccessDeniedException(JSONObject.toJSONString(data)));
    }



}

