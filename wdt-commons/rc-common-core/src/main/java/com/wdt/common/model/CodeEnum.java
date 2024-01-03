package com.wdt.common.model;

/**
 * Description:
 * Author: admin
 * Date: 2024/1/2
 */
public enum CodeEnum {
    SUCCESS(50001,"请求成功"),
    ERROR(50002,"请求失败,请联系管理员"),
    INTERIOR(50003,"内部接口，外部无法访问");
    private Integer code ;
    private String  msg ;

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
