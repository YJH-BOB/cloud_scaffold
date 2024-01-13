package com.wdt.security.feign.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wdt.common.factory.GenericFallbackFactory;
import com.wdt.security.feign.RcSystemServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RcSystemServiceClientImpl implements RcSystemServiceClient {

    @Autowired
    private RcSystemServiceClient rcSystemServiceClient;

    @Autowired
    private GenericFallbackFactory<RcSystemServiceClient> fallbackFactory;

    @Override
    public JSONObject findByUserName(String userName) {
        try {
            return rcSystemServiceClient.findByUserName(userName);
        } catch (Exception e) {
            return fallbackFactory.create(e).findByUserName(userName);
        }
    }

    @Override
    public JSONArray findByUserId(Long userId) {
        try {
            return rcSystemServiceClient.findByUserId(userId);
        } catch (Exception e) {
            return fallbackFactory.create(e).findByUserId(userId);
        }
    }
}
