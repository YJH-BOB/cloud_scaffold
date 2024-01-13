package com.wdt.security.feign;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wdt.common.config.FeignClientConfiguration;
import com.wdt.common.factory.GenericFallbackFactory;
import com.wdt.common.log.Log;
import com.wdt.common.model.Result;
import com.wdt.security.feign.impl.RcSystemServiceClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "rc-system",fallback = RcSystemServiceClientImpl.class,fallbackFactory = GenericFallbackFactory.class,configuration = FeignClientConfiguration.class)
public interface RcSystemServiceClient {
    // 根据用户名查询用户

    /**
     * 根据用户名查询用户
     * @param userName
     * @return
     */
     @PostMapping("findByUserName")
     JSONObject findByUserName(@RequestParam(value = "userName") String userName) ;

    /**
     * 根据用户id查询权限信息
     * @param userId
     * @return
     */
    @PostMapping("findByUserId")
    JSONArray findByUserId(@RequestParam(value = "userId") Long userId) ;



}
