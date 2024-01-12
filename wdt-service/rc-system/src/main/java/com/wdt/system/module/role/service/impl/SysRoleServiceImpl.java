package com.wdt.system.module.role.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdt.system.module.role.entity.SysRole;
import com.wdt.system.module.role.mapper.SysRoleMapper;
import com.wdt.system.module.role.service.SysRoleService;
import org.springframework.stereotype.Service;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
}
