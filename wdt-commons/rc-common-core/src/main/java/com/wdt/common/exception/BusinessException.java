package com.wdt.common.exception;

import com.wdt.common.enmus.CodeEnum;

/**
 * @author admin
 * 自定义业务异常，对运行时异常类进行扩展
 * 添加状态码
 */
public class BusinessException extends RuntimeException{

    private  CodeEnum codeEnum;

    private  Integer  code ;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(CodeEnum codeEnum) {
        super(codeEnum.msg);
        this.code= codeEnum.code;
    }
    public BusinessException() {
        super("未知异常");
    }
    public BusinessException(Integer code) {
        super("未知异常");
        this.code= code;
    }
    public CodeEnum getCodeEnum() {
        return codeEnum;
    }
    public Integer getCode() {
        return code;
    }
}
