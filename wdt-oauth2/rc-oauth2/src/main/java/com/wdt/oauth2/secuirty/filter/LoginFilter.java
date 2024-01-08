package com.wdt.oauth2.secuirty.filter;

import com.wdt.common.enmus.CodeEnum;
import com.wdt.common.exception.BusinessException;
import com.wdt.common.model.Result;
import com.wdt.common.utils.JwtUtil;
import com.wdt.common.utils.ResponseUtil;
import com.wdt.oauth2.module.user.entity.SysUser;
import com.wdt.oauth2.secuirty.entity.CustomUser;
import com.wdt.oauth2.utils.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/**
 *  登录过滤器，请求入口
 */
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private static final String usernameParameter = "username";
    private static final String passwordParameter = "password";

    public LoginFilter(AuthenticationManager authenticationManager,RedisUtil redisUtil) {
        this.authenticationManager = authenticationManager;
        this.redisUtil =  redisUtil ;
    }

    private final AuthenticationManager authenticationManager ;
    private final RedisUtil redisUtil ;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String userName = Optional.ofNullable(request.getParameter(usernameParameter)).orElseThrow(() -> new BusinessException(CodeEnum.DATA_NOT_FOUND));
        String passWord = Optional.ofNullable(request.getParameter(passwordParameter)).orElseThrow(() -> new BusinessException(CodeEnum.DATA_NOT_FOUND));
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, passWord, List.of());
        this.setDetails(request, authRequest);
        return authenticationManager.authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult)
            throws IOException, ServletException {

        CustomUser customUser = (CustomUser)authResult.getPrincipal();
        SysUser sysUser = customUser.getSysUser();
        String userName = sysUser.getUserName();
        String token = JwtUtil.createJWT(userName);
        redisUtil.setCacheObject(userName, token);
        Map<String,Object> userMap = new HashMap<>();
        userMap.put("token", token);
        userMap.put("user",sysUser);
        ResponseUtil.out(response, Result.succeed(userMap));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed)
            throws IOException, ServletException {
        ResponseUtil.out(response, Result.failed());
    }
}
