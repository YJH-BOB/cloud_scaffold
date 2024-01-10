package com.wdt.security.module.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdt.security.module.user.entity.SysUser;
import com.wdt.security.module.user.mapper.SysUserMapper;
import com.wdt.security.module.user.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

}