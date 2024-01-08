package com.wdt.common.utils;

import cn.hutool.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wdt.common.model.Result;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.MediaType;

import java.io.IOException;

/**
 * Description:
 * Author: admin
 * Date: 2024/1/8
 */
public class ResponseUtil {
    private ResponseUtil() {
    }

    public static void out(HttpServletResponse response,Result<?> result) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
