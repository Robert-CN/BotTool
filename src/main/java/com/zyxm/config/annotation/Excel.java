package com.zyxm.config.annotation;

import java.lang.annotation.*;

/**
 * @Author Robert
 * @Create 2020/9/17
 * @Desc TODO 报表注解
 **/
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Excel {
    String value() default "";
}
