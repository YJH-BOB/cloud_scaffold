package com.wdt.system.module.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdt.system.module.user.entity.SysUser;
import com.wdt.system.module.user.service.SysUserService;
import com.wdt.system.module.user.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

}