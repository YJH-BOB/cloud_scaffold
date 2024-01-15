package com.wdt.gateway.handler;


import com.wdt.common.utils.JWTUtil;
import com.wdt.gateway.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.authentication.ServerFormLoginAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * 鉴权入口
 */

@Slf4j
@Component
public class DefaultAuthenticationConverter extends ServerFormLoginAuthenticationConverter {
    @Autowired
    private RedisUtil redisUtil ;
    private static final String TOKEN = "token" ;
    private static final String ROLE = "role:" ;
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        System.out.println("从token中获取登陆用户信息");
        //从token中获取登陆用户信息
        List<String> tokenList = exchange.getRequest().getHeaders().get(TOKEN);
        if(tokenList==null) {
            log.error("用户认证信息为空,返回获取认证信息失败");
            return Mono.empty();
        } else {
            String token = tokenList.get(0);
            List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
            String usernameFromToken = JWTUtil.getUsernameFromToken(token);
            //获取权限信息
            if(StringUtils.isBlank(usernameFromToken)){
                log.error("用户认证信息为空,返回获取认证信息失败");
                return Mono.empty();
            }
            List<String> roles = redisUtil.getCacheList(ROLE+usernameFromToken);
            if(roles==null){
                System.out.println("token过期");
                return Mono.empty();
            }
            roles.forEach(role ->{
                SimpleGrantedAuthority auth = new SimpleGrantedAuthority(role);
                simpleGrantedAuthorities.add(auth);
            });
            Object sysUser = redisUtil.getCacheObject(usernameFromToken);
            UsernamePasswordAuthenticationToken  accountAuthentication = new UsernamePasswordAuthenticationToken(sysUser, token, simpleGrantedAuthorities);
            return Mono.just(accountAuthentication);
        }
    }
}
