package com.wdt.security.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wdt.common.enmus.CodeEnum;
import com.wdt.common.exception.BusinessException;
import com.wdt.security.enmus.AccountStatus;
import com.wdt.security.entity.DefaultUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultUserDetailsServiceImpl implements UserDetailsService {





    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        SysUser sysUser = Optional.ofNullable(username)
//                .map(u -> sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserName, u)))
//                .orElseThrow(() -> new BusinessException(CodeEnum.USER_NOT_FOUND));
//        if(AccountStatus.INACTIVE.getValue().equals(sysUser.getStatus())) {
//            throw new BusinessException(CodeEnum.ACCOUNT_DISABLED);
//        }
        JSONObject sysUser = new JSONObject();
        List<GrantedAuthority> authorities = new ArrayList<>() ;
        //  @TODO 通过role 表添加
        authorities.add( new DefaultGrantedAuthorityImpl("ROLE_ADMIN"));
        authorities.add( new DefaultGrantedAuthorityImpl("AUTH_WRITE"));
        return new DefaultUser(sysUser, authorities);
    }
}
