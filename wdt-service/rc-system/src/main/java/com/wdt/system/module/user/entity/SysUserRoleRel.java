package com.wdt.system.module.user.entity;

import lombok.Data;

@Data
public class SysUserRoleRel {
    /**
     * 主键id
     */
    private Integer id;
    
    /**
     * 用户id
     */
    private Integer userId;
    
    /**
     * 角色id
     */
    private Integer roleId;
}
