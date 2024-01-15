package com.wdt.security.filter;

import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSONObject;
import com.wdt.common.enmus.CodeEnum;
import com.wdt.common.exception.BusinessException;
import com.wdt.common.model.Result;
import com.wdt.common.utils.JWTUtil;
import com.wdt.common.utils.ResponseUtil;
import com.wdt.security.entity.DefaultUser;
import com.wdt.security.utils.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.*;

/**
 * 登录入口
 */

public class LoginFilter extends UsernamePasswordAuthenticationFilter {


    private static final String ROLE = "role:" ;

    private static final String usernameParameter = "username";
    private static final String passwordParameter = "password";

    private final RedisUtil redisUtil ;

    public LoginFilter(AuthenticationManager authenticationManager,
                       RedisUtil redisUtil){
        super(authenticationManager);
        this.redisUtil = redisUtil;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String userName = Optional.ofNullable(request.getParameter(usernameParameter)).orElseThrow(() -> new BusinessException(CodeEnum.DATA_NOT_FOUND));
        String passWord = Optional.ofNullable(request.getParameter(passwordParameter)).orElseThrow(() -> new BusinessException(CodeEnum.DATA_NOT_FOUND));
        // @TODO 前端密码解密
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, passWord, List.of());
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        DefaultUser defaultUser = (DefaultUser)auth.getPrincipal();
        JSONObject userInfo = defaultUser.getUserInfo();
        String userName = userInfo.getString("userName");
        String token = JWTUtil.createToken(userInfo.getString("userName"));
        redisUtil.setCacheObject(userName, token);
        Map<String,Object> userMap = new HashMap<>();
        userMap.put("token", token);
        userMap.put("user",userInfo);
        redisUtil.setCacheObject(userName, userMap);
        response.setStatus(HttpStatus.HTTP_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Collection<? extends GrantedAuthority> authorities = defaultUser.getAuthorities();
        redisUtil.setCacheObject(ROLE+userName, authorities);
        ResponseUtil.out(response, Result.succeed(userMap,200,CodeEnum.LOGIN_IN.getMsg()));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException, ServletException {
        response.setStatus(HttpStatus.HTTP_BAD_REQUEST);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ResponseUtil.out(response, Result.succeed(200, CodeEnum.USER_NOT_FOUND.getMsg()));
    }

}
