package com.wdt.secuirty.secuirty.filter;

import com.alibaba.fastjson.JSON;
import com.wdt.common.enmus.CodeEnum;
import com.wdt.common.exception.BusinessException;
import com.wdt.common.utils.JWTUtil;
import com.wdt.secuirty.secuirty.entity.DefaultUser;
import com.wdt.secuirty.utils.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;


/**
 * 授权过滤器
 */
public class LoginAuthFilter extends BasicAuthenticationFilter {
    private static final String TOKEN = "token" ;

    private RedisUtil redisUtil ;
    public LoginAuthFilter(AuthenticationManager authenticationManager,RedisUtil redisUtil) {
        super(authenticationManager);
        this.redisUtil = redisUtil ;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(TOKEN);
        Optional.ofNullable(token).orElseThrow(()->new BusinessException(CodeEnum.UNAUTHORIZE));


        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(TOKEN);
        return Optional.ofNullable(JWTUtil.getUsernameFromToken(token))
                .map(userName -> {
                    String username = JWTUtil.getUsernameFromToken(userName);
                    Map<String, Object> cacheMap = redisUtil.getCacheMap(username);
                    Object userObj = cacheMap.get("user");
                    DefaultUser defaultUser = JSON.parseObject(JSON.toJSONString(userObj), DefaultUser.class);
                    // 查下token 到期时间
                     //@TODO token刷新
                    return new UsernamePasswordAuthenticationToken(userName, token, defaultUser.getAuthorities());
                })
                .filter(authenticationToken -> !authenticationToken.getAuthorities().isEmpty())
                .orElseThrow(() -> new BusinessException(CodeEnum.DATA_NOT_FOUND));
    }

}