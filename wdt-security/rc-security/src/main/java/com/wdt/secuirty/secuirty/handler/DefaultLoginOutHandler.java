package com.wdt.secuirty.secuirty.handler;

import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.wdt.common.enmus.CodeEnum;
import com.wdt.common.exception.BusinessException;
import com.wdt.common.model.Result;
import com.wdt.common.utils.JWTUtil;
import com.wdt.secuirty.utils.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 默认的登出处理器
 */

@Component
public class DefaultLoginOutHandler implements LogoutHandler {
    @Autowired
    private RedisUtil redisUtil ;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader("token");
        Optional.ofNullable(token).ifPresent(i -> {
            try {
                String username = JWTUtil.getUsernameFromToken(token);
                redisUtil.deleteObject(username);
                response.setStatus(HttpStatus.HTTP_OK);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                String message = JSONUtil.toJsonStr(Result.succeed(200,CodeEnum.LOGIN_OUT.getMsg()));
                response.getWriter().write(message);
            } catch (Exception e) {
                throw new BusinessException(CodeEnum.TOKEN_NOT_FOUND);
            }
        });
    }
}
