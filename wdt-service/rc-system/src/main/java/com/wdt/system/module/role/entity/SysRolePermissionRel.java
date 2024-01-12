package com.wdt.system.module.role.entity;

import lombok.Data;

@Data
public class SysRolePermissionRel {
    /**
     * 主键id
     */
    private Integer id;
    
    /**
     * 角色id
     */
    private Integer roleId;
    
    /**
     * 权限id
     */
    private Integer permissionId;
}
