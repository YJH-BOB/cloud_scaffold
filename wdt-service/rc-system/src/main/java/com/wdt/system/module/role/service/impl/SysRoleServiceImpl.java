package com.wdt.system.module.role.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdt.system.module.role.entity.SysRole;
import com.wdt.system.module.role.mapper.SysRoleMapper;
import com.wdt.system.module.role.service.SysRoleService;
import com.wdt.system.module.user.service.SysUserRoleRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysUserRoleRelService sysUserRoleRelService ;
    @Override
    public List<SysRole> findByUserId(Long userId) {
        return  baseMapper.findByUserId(userId);
    }
}
