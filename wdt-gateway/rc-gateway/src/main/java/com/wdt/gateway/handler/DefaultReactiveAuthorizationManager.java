//package com.wdt.gateway.handler;
//
//import com.alibaba.fastjson.JSONObject;
//import com.wdt.common.enmus.CodeEnum;
//import com.wdt.common.model.Result;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.authorization.AuthorizationDecision;
//import org.springframework.security.authorization.ReactiveAuthorizationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.server.authorization.AuthorizationContext;
//import reactor.core.publisher.Mono;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// *  权限校验，实现此角色是不是有这个目录的权限
// *
// **/
//@Slf4j
//public class DefaultReactiveAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext>  {
//
//    private final List<String> authorities = new ArrayList<>();
//
//    public DefaultReactiveAuthorizationManager(String authority, String... authorities ) {
//        this.authorities.add(authority);
//        if(authorities != null) {
//            this.authorities.addAll(Arrays.asList(authorities));
//        }
//    }
//
//
//    @Override
//    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
//
//        return authentication
//                .filter(Authentication::isAuthenticated)
//                .flatMapIterable(Authentication::getAuthorities)
//                .map(GrantedAuthority::getAuthority)
//                .any(c -> {
//                    //检测权限是否匹配
//                   log.info("角色: " + c);
//                    if (authorities.contains(c)) {
//                        return true;
//                    }
//                    Result<Object> data = Result.failed(CodeEnum.UNAUTHORIZE.code, CodeEnum.UNAUTHORIZE.getMsg());
//                    throw new AccessDeniedException(JSONObject.toJSONString(data));
//                })
//                .map(AuthorizationDecision::new)
//                .defaultIfEmpty(new AuthorizationDecision(false));
//    }
//}
