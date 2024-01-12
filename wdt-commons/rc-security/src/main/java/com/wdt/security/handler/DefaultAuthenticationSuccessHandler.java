//package com.wdt.security.handler;
//
//import cn.hutool.http.HttpStatus;
//import com.alibaba.fastjson.JSON;
//import com.wdt.common.enmus.CodeEnum;
//import com.wdt.common.model.Result;
//import com.wdt.common.utils.ResponseUtil;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//@Component
//public class DefaultAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        response.setStatus(HttpStatus.HTTP_OK);
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        ResponseUtil.out(response,Result.succeed(200, "认证成功"));
//    }
//}
