package com.wdt.security.security.handler;

/**
 * Description:
 * Author: admin
 * Date: 2024/1/10
 */

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.wdt.common.enmus.CodeEnum;
import com.wdt.common.model.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 未认证，统一处理器
 */
@Component
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String message = JSONUtil.toJsonStr(Result.failed(401, CodeEnum.UNAUTHORIZE.getMsg()));
        response.getWriter().write(message);
    }
}
