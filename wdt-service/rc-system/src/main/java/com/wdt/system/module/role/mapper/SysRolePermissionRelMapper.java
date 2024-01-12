package com.wdt.system.module.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wdt.system.module.permission.entity.SysPermission;
import com.wdt.system.module.role.entity.SysRolePermissionRel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysRolePermissionRelMapper extends BaseMapper<SysRolePermissionRel> {
}
