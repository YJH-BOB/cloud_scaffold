package com.wdt.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wdt.common.model.Result;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private ResponseUtil() {
    }

    public static void out(HttpServletResponse response, Result<?> result) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
