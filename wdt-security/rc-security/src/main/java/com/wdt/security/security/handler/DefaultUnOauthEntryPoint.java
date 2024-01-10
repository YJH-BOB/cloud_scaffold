package com.wdt.security.security.handler;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.wdt.common.enmus.CodeEnum;
import com.wdt.common.model.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
/**
 * 默认未授权统一处理类
 */
@Component
public class DefaultUnOauthEntryPoint  implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String message = JSONUtil.toJsonStr(Result.failed(401, CodeEnum.UNAUTHORIZE.getMsg()));
        response.getWriter().write(message);
    }
}
