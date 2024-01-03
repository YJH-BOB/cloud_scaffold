package com.wdt.common.exception;

import com.wdt.common.enmus.BusinessExceptionEnum;

public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(BusinessExceptionEnum businessExceptionEnum) {
        super(BusinessExceptionEnum.SQL_ERROR.getMsg());
    }


}
