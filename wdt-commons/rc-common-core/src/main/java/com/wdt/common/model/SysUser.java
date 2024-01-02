package com.wdt.common.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * Description:
 * Author: admin
 * Date: 2024/1/2
 */
@Data
public class SysUser {
    private Long id;
    private Long platformId;
    private String username;
    private String name;
    private String password;
    private String salt;
    private String email;
    private String mobile;
    private Integer status;
    private Integer createUserId;
    private Timestamp gmtCreate;
    private Integer modifiedUserId;
    private Timestamp gmtModified;
    private Integer isDeleted;
}
