package com.wdt.common.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class BaseEntity {
    /**
     * 创建人的用户id
     */
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;
    /**
     * 创建时间
     */
    @JsonIgnore
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更新人
     */
    @JsonIgnore
    @TableField(fill = FieldFill.UPDATE)
    private Long updateUser;
    /**
     * 更新时间
     */
    @JsonIgnore
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;
    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    @JsonIgnore
    @TableLogic
    private Integer isDelete;
}
