package com.wdt.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Description:
 * Date: 2024/1/2
 *
 * @author admin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private T data;
    private Integer code;
    private String msg;

    private static <T> Result<T> of(T data, Integer code, String msg) {
        return new Result<>(data, code, msg);
    }

    //提供返回成功的方法
    public static <T> Result<T> succeed() {
        return new Result<>(null, CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg());
    }

    public static <T> Result<T> succeed(T data) {
        return new Result<>(data, CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg());
    }

    public static <T> Result<T> succeed(T data, Integer code) {
        return new Result<>(data, code, CodeEnum.SUCCESS.getMsg());
    }

    public static <T> Result<T> succeed(T data, Integer code, String msg) {
        return new Result<>(data, code, msg);
    }


    public static <T> Result<T> failed() {
        return new Result<>(null, CodeEnum.ERROR.getCode(), CodeEnum.ERROR.getMsg());
    }

    public static <T> Result<T> failed(T data) {
        return new Result<>(data, CodeEnum.ERROR.getCode(), CodeEnum.ERROR.getMsg());
    }

    public static <T> Result<T> failed(T data, Integer code) {
        return new Result<>(data, code, CodeEnum.ERROR.getMsg());
    }

    public static <T> Result<T> failed(T data, Integer code, String msg) {
        return new Result<>(data, code, msg);
    }

}
