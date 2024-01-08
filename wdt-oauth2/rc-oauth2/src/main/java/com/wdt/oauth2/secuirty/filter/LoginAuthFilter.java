package com.wdt.oauth2.secuirty.filter;

import com.wdt.common.enmus.CodeEnum;
import com.wdt.common.exception.BusinessException;
import com.wdt.common.utils.JwtUtil;
import com.wdt.oauth2.utils.RedisUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * 权限过虑器
 */
public class LoginAuthFilter extends BasicAuthenticationFilter {
    private static final String TOKEN = "token" ;
    private final RedisUtil redisUtil ;
    public LoginAuthFilter(AuthenticationManager authenticationManager,RedisUtil redisUtil) {
        super(authenticationManager);
        this.redisUtil =  redisUtil ;
    }


    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        // 获取权限信息
        Authentication authentication = getAuthentication(request);

        Optional.ofNullable(authentication)
                .ifPresent(SecurityContextHolder.getContext()::setAuthentication);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(TOKEN);
        return Optional.ofNullable(JwtUtil.parseJWT(token))
                .map(userName -> {
                    List<String> cacheList = redisUtil.getCacheList("role" + userName);
                    List<GrantedAuthority> grantedAuthorities = cacheList.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
                    return new UsernamePasswordAuthenticationToken(userName, token, grantedAuthorities);
                })
                .filter(authenticationToken -> !authenticationToken.getAuthorities().isEmpty())
                .orElseThrow(() -> new BusinessException(CodeEnum.DATA_NOT_FOUND));
    }
}
