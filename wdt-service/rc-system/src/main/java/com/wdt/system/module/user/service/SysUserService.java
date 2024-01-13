package com.wdt.system.module.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wdt.system.module.user.entity.SysUser;

public interface SysUserService extends IService<SysUser> {

    SysUser findByUserName(String userName);
}