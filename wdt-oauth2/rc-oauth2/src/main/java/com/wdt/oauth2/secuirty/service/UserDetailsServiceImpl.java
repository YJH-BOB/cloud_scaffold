package com.wdt.oauth2.secuirty.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wdt.common.enmus.CodeEnum;
import com.wdt.common.exception.BusinessException;
import com.wdt.oauth2.module.user.entity.SysUser;
import com.wdt.oauth2.module.user.mapper.SysUserMapper;
import com.wdt.oauth2.secuirty.entity.CustomUser;
import com.wdt.oauth2.secuirty.eumus.AccountStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

/**
 * Description: UserDetailsService的实现类
 * Author: admin
 * Date: 2024/1/8
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper ;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = Optional.ofNullable(username)
                .map(u -> sysUserMapper.selectOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserName, u)))
                .orElseThrow(() -> new BusinessException(CodeEnum.USER_NOT_FOUND));
        if(AccountStatus.INACTIVE.getValue().equals(sysUser.getStatus())) {
            throw new BusinessException(CodeEnum.ACCOUNT_DISABLED);
        }
        return new CustomUser(sysUser, Collections.emptyList());
    }
}
