package com.wdt.security.feign;

import com.wdt.common.config.FeignClientConfiguration;
import com.wdt.common.factory.GenericFallbackFactory;
import com.wdt.security.feign.impl.RcSystemServiceClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(name = "rc-system",fallback = RcSystemServiceClientImpl.class,fallbackFactory = GenericFallbackFactory.class,configuration = FeignClientConfiguration.class)
public interface RcSystemServiceClient {
    // 根据用户名查询用户

    



    // 根据用户id查询权限信息

}
