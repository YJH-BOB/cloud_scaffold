package com.wdt.oauth2.module.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdt.oauth2.module.user.entity.SysUser;
import com.wdt.oauth2.module.user.mapper.SysUserMapper;
import com.wdt.oauth2.module.user.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

}