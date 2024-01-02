package com.wdt.common.log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:
 * Author: admin
 * Date: 2024/1/2
 */
@Retention(RetentionPolicy.RUNTIME) // 声明注解的保留策略
@Target(ElementType.METHOD) // 声明注解的作用目标
public @interface Log {
    String value() default "" ;

}
