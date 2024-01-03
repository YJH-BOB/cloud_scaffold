package com.wdt.common.model;

import com.wdt.common.enmus.CodeEnum;
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

    private String status;
    private T data;
    private Integer code;
    private String msg;

    private static <T> Result<T> of(String status, T data, Integer code, String msg) {
        return new Result<>(status, data, code, msg);
    }

    //提供返回成功的方法
    public static Result<Object> succeed() {
        return Result.of("success", null, CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg());
    }

    public static <T> Result<T> succeed(T data) {
        return Result.of("success", data, CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMsg());
    }

    public static <T> Result<T> succeed(T data, Integer code) {
        return Result.of("success", data, code, CodeEnum.SUCCESS.getMsg());
    }

    public static <T> Result<T> succeed(T data, Integer code, String msg) {
        return Result.of("success", data, code, msg);
    }

    public static Result<Object> succeed(Integer code, String msg) {
        return Result.of("success", null, code, msg);
    }


    public static Result<Object> failed() {
        return Result.of("fail", null, CodeEnum.ERROR.getCode(), CodeEnum.ERROR.getMsg());
    }

    public static <T> Result<T> failed(T data) {
        return Result.of("fail", data, CodeEnum.ERROR.getCode(), CodeEnum.ERROR.getMsg());
    }

    public static <T> Result<T> failed(T data, Integer code) {
        return Result.of("fail", data, code, CodeEnum.ERROR.getMsg());
    }

    public static <T> Result<T> failed(T data, Integer code, String msg) {
        return Result.of("fail", data, code, msg);
    }

    public static Result<Object> failed(Integer code, String msg) {
        return Result.of("fail", null, code, msg);
    }

}
