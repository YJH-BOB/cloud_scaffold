package com.wdt.oauth2.secuirty.oauth2;

import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wdt.common.model.Result;
import com.wdt.common.utils.JwtUtil;
import com.wdt.common.utils.ResponseUtil;
import com.wdt.oauth2.utils.RedisUtil;
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
                response.setStatus(HttpStatus.HTTP_OK);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                ResponseUtil.out(response,Result.succeed(200,"登出成功"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
