package com.wdt.system.module.role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wdt.system.module.role.entity.SysRole;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {
    List<SysRole> findByUserId(Long userId);
}
