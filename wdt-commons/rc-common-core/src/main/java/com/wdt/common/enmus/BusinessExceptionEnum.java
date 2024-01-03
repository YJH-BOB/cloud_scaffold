package com.wdt.common.enmus;

public enum BusinessExceptionEnum {

   // --------------   sql相关 code范围为 10000-12000  -----------------
    SQL_ERROR(10001,"sql异常"),
    // --------------  数据相关 code范围为 12000-14000  -----------------
    DATA_ERROR(12001,"数据异常");

    Integer code ;

    String  msg  ;

    BusinessExceptionEnum(Integer code, String msg) {
        this.code=code;
        this.msg=msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
