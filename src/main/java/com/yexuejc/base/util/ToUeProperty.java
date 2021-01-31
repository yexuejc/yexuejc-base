package com.yexuejc.base.util;

import java.lang.annotation.*;

/**
 * json转换时的格式指定
 *
 * @author: yexuejc
 * @date: 2021-01-31 12:48:29
 */
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ToUeProperty {
    /**
     * 字段名，默认该字段转下划线
     *
     * @return
     */
    String value() default "";

    /**
     * 是否忽略该字段，默认false
     *
     * @return
     */
    boolean ignore() default false;
}