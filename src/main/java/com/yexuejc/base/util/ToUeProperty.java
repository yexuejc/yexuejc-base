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

    /**
     * 转换格式
     * <p>目前只支持</p>
     * <p> {@link java.util.Date}、{@link java.time.LocalDate}、{@link java.time.LocalDateTime}
     * 转{@link Integer}(10位长度)、{@link Long}(13位长度)</p>
     * 其余无效
     *
     * @return <pre>
     * this                       in        out <br/>
     * {@link java.util.Date}             {@link Integer}   {@link Integer}10位长度时间戳<br/>
     * {@link java.util.Date}             {@link Long}      {@link Long}13位长度时间戳<br/>
     * {@link java.time.LocalDate}        {@link Integer}   {@link Integer}10位长度时间戳<br/>
     * {@link java.time.LocalDate}        {@link Long}      {@link Long}13位长度时间戳（后三位为000）<br/>
     * {@link java.time.LocalDateTime}     {@link Integer}   {@link Integer}10位长度时间戳<br/>
     * {@link java.time.LocalDateTime}     {@link Long}     {@link Long}13位长度时间戳
     *
     * </pre>
     */
    Class<?> type() default ObjUtil.class;

}