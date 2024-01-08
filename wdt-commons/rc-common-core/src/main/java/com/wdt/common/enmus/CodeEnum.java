package com.wdt.common.enmus;

/**
 * Description:
 * Author: admin
 * Date: 2024/1/2
 */
@SuppressWarnings("all")
public enum CodeEnum {
    SUCCESS(50001, "请求成功"),
    ERROR(50002, "请求失败,请联系管理员"),
    INTERIOR(50003, "内部接口，外部无法访问"),
    // --------------   sql相关 code范围为 10000-12000  -----------------
    SQL_ERROR(10001, "sql异常"),
    // --------------  数据相关 code范围为 12000-14000  -----------------
    DATA_ERROR(12001, "数据异常"),

    DATA_NOT_FOUND(12002, "数据不存在"),

    //======================= 用户相关 code范围为 14000-16000=========================================
    USER_NOT_FOUND(14001, "用户不存在"),

    //======================= 账号相关 code范围为 16000-18000=========================================
    ACCOUNT_DISABLED(16001, "账号已停用");

    public Integer code;
    public String msg;

    CodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
