package com.yexuejc.base.util;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ToUeProperty {
    /**
     * 字段名，默认该字段转下划线
     * @return
     */
    String value() default "";
}