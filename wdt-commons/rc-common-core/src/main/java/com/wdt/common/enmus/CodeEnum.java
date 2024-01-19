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

    TOKEN_NOT_FOUND(12004, "TOKEN不存在"),

    //======================= 用户相关 code范围为 14000-16000=========================================
    USER_NOT_FOUND(14001, "用户不存在"),

    //======================= 账号相关 code范围为 16000-18000=========================================
    ACCOUNT_DISABLED(16001, "账号已停用"),

    LOGIN_OUT(16001, "登出成功"),

    LOGIN_IN(16002, "登录成功"),

    TOKEN_MISSION(16003,"token不存在"),

    //======================= 权限相关 code范围为 18000-20000=========================================

    UNAUTHORIZE(18001, "未授权禁止访问"),

    TOKEN_INVALID(18002,"token失效"),


    //======================= minio相关 code范围为 20001-22000=========================================
    DOWNLOAD_FIFE_ERR(20006,"文件下载失败"),
    UPLOAD_FIFE_ERR(20006,"文件上传失败"),
    GET_OBJECT_ERR(20005,"获取文件对象失败"),

    LIST_BUCKET_ERR(20004,"获取所有桶失败"),
    REMOVE_BUCKET_ERR(20003, "删除桶存在失败"),
    BUCKET_EXISTS_ERR(20002, "查询桶存在失败"),
    MAKE_BUCKET_ERR(20001, "创建桶失败");

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
