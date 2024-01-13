package com.wdt.security.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wdt.common.enmus.CodeEnum;
import com.wdt.common.exception.BusinessException;
import com.wdt.security.enmus.AccountStatus;
import com.wdt.security.entity.DefaultUser;
import com.wdt.security.feign.RcSystemServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultUserDetailsServiceImpl implements UserDetailsService {
    private static final String STATUS = "status";
    private static final String ID = "id";
    private static final String ROLE_NAME = "roleName";

    @Autowired
    private RcSystemServiceClient rcSystemServiceClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        JSONObject sysUser = rcSystemServiceClient.findByUserName(username);
        if (sysUser == null) {
            throw new BusinessException(CodeEnum.USER_NOT_FOUND);
        }
        if (AccountStatus.INACTIVE.getValue().equals(sysUser.getString(STATUS))) {
            throw new BusinessException(CodeEnum.ACCOUNT_DISABLED);
        }
        Long userId = sysUser.getLong(ID);
        JSONArray roleList = rcSystemServiceClient.findByUserId(userId);
        List<GrantedAuthority> authorities = roleList.stream()
                .map(i -> (JSONObject) i)
                .map(role -> new DefaultGrantedAuthorityImpl(role.getString(ROLE_NAME)))
                .collect(Collectors.toList());
        return new DefaultUser(sysUser, authorities);
    }
}
