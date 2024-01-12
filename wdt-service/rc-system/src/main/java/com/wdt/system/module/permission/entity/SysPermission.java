package com.wdt.system.module.permission.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wdt.common.model.BaseEntity;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_permission")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysPermission extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -6609385184236112192L;
    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属上级
     */
    @TableField("pid")
    private Long pid;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 访问路径
     */
    @TableField("path")
    private String path;

    /**
     * 类型(1:菜单,2:按钮)
     */
    @TableField("type")
    private Integer type;

    /**
     * 组件路径
     */
    @TableField("component")
    private String component;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 权限值
     */
    @TableField("permission_value")
    private String permissionValue;

    /**
     * 状态(0:禁止,1:正常)
     */
    @TableField("status")
    private Integer status;

    /**
     * 层级
     */
    @TableField("level")
    private Integer level;

    /**
     * 是否选中
     */
    @TableField("is_select")
    private Integer isSelect;


}
