//package com.wdt.security.filter;
//
//import com.alibaba.fastjson.JSON;
//import com.wdt.common.enmus.CodeEnum;
//import com.wdt.common.exception.BusinessException;
//import com.wdt.common.utils.JWTUtil;
//import com.wdt.security.entity.DefaultUser;
//import com.wdt.security.utils.RedisUtil;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.util.StringUtils;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.Optional;
//
//
///**
// * 授权过滤器
// */
//
//public class LoginAuthFilter extends BasicAuthenticationFilter {
//    private static final String TOKEN = "token" ;
//
//    private final RedisUtil redisUtil ;
//    public LoginAuthFilter(AuthenticationManager authenticationManager,RedisUtil redisUtil) {
//        super(authenticationManager);
//        this.redisUtil = redisUtil ;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String token = request.getHeader(TOKEN);
//        //获取token
//        if (!StringUtils.hasText(token)) {
//            //放行
//            chain.doFilter(request, response);
//            return;
//        }
//        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        chain.doFilter(request, response);
//    }
//    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
//        String token = request.getHeader(TOKEN);
//        return Optional.ofNullable(JWTUtil.getUsernameFromToken(token))
//                .map(userName -> {
//                    String username = JWTUtil.getUsernameFromToken(userName);
//                    Map<String, Object> cacheMap = redisUtil.getCacheMap(username);
//                    Object userObj = cacheMap.get("user");
//                    DefaultUser defaultUser = JSON.parseObject(JSON.toJSONString(userObj), DefaultUser.class);
//                    // 查下token 到期时间
//                     //@TODO token刷新
//                    return new UsernamePasswordAuthenticationToken(userName, token, defaultUser.getAuthorities());
//                })
//                .filter(authenticationToken -> !authenticationToken.getAuthorities().isEmpty())
//                .orElseThrow(() -> new BusinessException(CodeEnum.DATA_NOT_FOUND));
//    }
//
//}
