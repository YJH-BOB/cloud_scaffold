package com.wdt.system.module.role.entity;

import com.wdt.common.model.BaseEntity;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SysRole extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -2820745155987630222L;
    /**
     * 主键id
     */
    private Long id;
    
    /**
     * 角色编号
     */
    private String roleCode;
    
    /**
     * 角色名
     */
    private String roleName;
    
    /**
     * 角色说明
     */
    private String roleDescription;

}
