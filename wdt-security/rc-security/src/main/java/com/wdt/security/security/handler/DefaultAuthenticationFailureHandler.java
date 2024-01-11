package com.wdt.security.security.handler;

import cn.hutool.http.HttpStatus;
import com.alibaba.fastjson.JSON;
import com.wdt.common.model.Result;
import com.wdt.common.utils.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class DefaultAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.HTTP_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ResponseUtil.out(response,Result.succeed(401, "认证失败"));
    }
}
