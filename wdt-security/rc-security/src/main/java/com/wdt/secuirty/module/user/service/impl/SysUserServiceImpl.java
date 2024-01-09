package com.wdt.secuirty.module.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdt.secuirty.module.user.entity.SysUser;
import com.wdt.secuirty.module.user.mapper.SysUserMapper;
import com.wdt.secuirty.module.user.service.SysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

}