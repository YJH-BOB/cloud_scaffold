package com.wdt.common.factory;

import com.wdt.common.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class GenericFallbackFactory<T> implements FallbackFactory<T> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public T create(Throwable cause) {
        Throwable rootCause = getRootCause(cause);
        logger.error("Feign调用发生异常: {}", rootCause.getMessage(), rootCause);
        throw new BusinessException("Feign调用发生异常",rootCause);
    }

    /**
     * 获取根异常
     */
    private Throwable getRootCause(Throwable cause) {
        Throwable rootCause = cause;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }
}
