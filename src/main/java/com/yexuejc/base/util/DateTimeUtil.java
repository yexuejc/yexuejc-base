package com.yexuejc.base.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * 新版时间日期出来 工具
 *
 * @author: maxf
 * @date: 2018/3/27 10:44
 */
public class DateTimeUtil {
    /**
     * 获取本年第一天
     *
     * @return
     */
    public static LocalDate getYear4First() {
        return getYear4First(LocalDate.now());
    }

    /**
     * 指定日期 年第一天
     *
     * @param now
     * @return
     */
    public static LocalDate getYear4First(LocalDate now) {
        return now.with(TemporalAdjusters.firstDayOfYear());
    }

    /**
     * 获取本年最后一天
     *
     * @return
     */
    public static LocalDate getYear4Last() {
        return getYear4Last(LocalDate.now());
    }

    /**
     * 指定日期 年最后一天
     *
     * @param now
     * @return
     */
    public static LocalDate getYear4Last(LocalDate now) {
        return now.with(TemporalAdjusters.lastDayOfYear());
    }


    /**
     * 获取本月第一天
     *
     * @return
     */
    public static LocalDate getMonth4First() {
        return getMonth4First(LocalDate.now());
    }

    /**
     * 指定日期 月第一天
     *
     * @param now
     * @return
     */
    public static LocalDate getMonth4First(LocalDate now) {
        return now.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 获取本月第最后一天
     *
     * @return
     */
    public static LocalDate getMonth4Last() {
        return getMonth4Last(LocalDate.now());
    }

    /**
     * 指定日期 月第最后一天
     *
     * @param now
     * @return
     */
    public static LocalDate getMonth4Last(LocalDate now) {
        return now.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 获取本周第一天
     *
     * @return {@link LocalDate}
     */
    public static LocalDate getWeek4First() {
        return getWeek4First(LocalDate.now());
    }

    /**
     * 指定日期 周第一天
     *
     * @param date
     * @return
     */
    public static LocalDate getWeek4First(LocalDate date) {
        TemporalAdjuster FIRST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.minusDays(localDate.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue()));
        return date.with(FIRST_OF_WEEK);
    }

    /**
     * 获取本周最后一天
     *
     * @return
     */
    public static LocalDate getWeek4Last() {
        return getWeek4Last(LocalDate.now());
    }

    /**
     * 指定日期 周最后一天
     *
     * @param date
     * @return
     */
    public static LocalDate getWeek4Last(LocalDate date) {
        TemporalAdjuster LAST_OF_WEEK = TemporalAdjusters.ofDateAdjuster(localDate -> localDate.plusDays(DayOfWeek.SUNDAY.getValue() - localDate.getDayOfWeek().getValue()));
        return date.with(LAST_OF_WEEK);
    }

    /**
     * ZonedDateTime 转 Date
     *
     * @param zonedDateTime
     * @return
     */
    public static Date zonedDateTime2Date(ZonedDateTime zonedDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = zonedDateTime.withZoneSameInstant(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    /**
     * Date 转 ZonedDateTime
     *
     * @param date
     * @return
     */
    public static ZonedDateTime date2ZonedDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).withZoneSameInstant(zoneId);
    }

    /**
     * 格式化时间 <br/>
     * 格式 yyyy-MM-dd HH:mm:ss
     *
     * @param dateTime
     * @return
     */
    public static String format(LocalDateTime dateTime) {
        return format(dateTime, "");
    }

    /**
     * 格式化时间
     *
     * @param dateTime
     * @param pattern  格式 默认:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        if (pattern.isEmpty()) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(dateTime);
    }

    public static void main(String[] args) {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println(df.format(zonedDateTime2Date(ZonedDateTime.now())));
//        System.out.println(df2.format(date2ZonedDateTime(new Date())));

//        System.out.println(getWeek4First());
        System.out.println(format(getWeek4First(LocalDate.parse("2018-02-10")).atTime(LocalTime.MIN)));
        System.out.println(format(getWeek4Last(LocalDate.parse("2018-02-10")).atTime(LocalTime.MAX)));

//        System.out.println(format(getMonth4First().atTime(LocalTime.MIN)));
//        System.out.println(format(getMonth4Last().atTime(LocalTime.MAX)));

//        System.out.println(format(getYear4First().atTime(LocalTime.MIN)));
//        System.out.println(format(getYear4Last().atTime(LocalTime.MAX)));

    }


}
