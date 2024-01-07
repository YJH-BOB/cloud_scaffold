package com.wdt.oauth2.secuirty;

import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wdt.common.model.Result;
import com.wdt.common.util.JwtUtil;
import com.wdt.common.util.RedisUtil;
import io.jsonwebtoken.Claims;
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
            // 移除redis中的值
            try {
                Claims claims = JwtUtil.parseJWT(token);
                String username = (String) claims.get("username");
                redisUtil.deleteObject(username);
                ObjectMapper objectMapper = new ObjectMapper();
                response.setStatus(HttpStatus.HTTP_UNAUTHORIZED);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.getWriter().write(objectMapper.writeValueAsString(Result.failed()));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
    }
}
