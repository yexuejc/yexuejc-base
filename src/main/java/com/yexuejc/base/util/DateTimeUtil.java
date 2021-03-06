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
    public static String DATE_PATTERN = "yyyy-MM-dd";
    public static String TIME_PATTERN = "HH:mm:ss";
    public static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_TIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    private DateTimeUtil() {
    }

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
        TemporalAdjuster firstOfWeek = TemporalAdjusters.ofDateAdjuster(localDate ->
                localDate.minusDays(localDate.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue()));
        return date.with(firstOfWeek);
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
        TemporalAdjuster lastOfWeek = TemporalAdjusters.ofDateAdjuster(localDate ->
                localDate.plusDays(DayOfWeek.SUNDAY.getValue() - localDate.getDayOfWeek().getValue()));
        return date.with(lastOfWeek);
    }

    /**
     * ZonedDateTime 转 Date
     *
     * @param zonedDateTime
     * @return
     */
    public static Date parseDate(ZonedDateTime zonedDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = zonedDateTime.withZoneSameInstant(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    /**
     * LocalDate 转 Date
     *
     * @param localDate
     * @return
     */
    public static Date parseDate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * LocalDateTime 转 Date
     *
     * @param localDateTime
     * @return
     */
    public static Date parseDate(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * LocalTime 转 Date
     *
     * @param localTime 时间
     * @return 当前日期的指定时间
     */
    public static Date parseDate(LocalTime localTime) {
        LocalDate localDate = LocalDate.now();
        return parseDate(localDate, localTime);
    }

    /**
     * LocalDate + LocalTime 转 Date
     *
     * @param localDate 日期
     * @param localTime 时间
     * @return 指定日期的指定时间
     */
    public static Date parseDate(LocalDate localDate, LocalTime localTime) {
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * Date 转 ZonedDateTime
     *
     * @param date
     * @return
     */
    public static ZonedDateTime parseZonedDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).withZoneSameInstant(zoneId);
    }

    /**
     * Date 转 LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime parseLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * Date 转 LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate parseLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    /**
     * Date 转 LocalTime
     *
     * @param date
     * @return
     */
    public static LocalTime parseLocalTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalTime();
    }

    /**
     * Long 转 LocalDateTime
     *
     * @param timestamp 13位（毫秒）
     * @return
     */
    public static LocalDateTime parseLocalDateTime13(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * Long 转 LocalDateTime
     *
     * @param timestamp 10位（秒）
     * @return
     */
    public static LocalDateTime parseLocalDateTime10(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp * 1000);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * LocalDateTime 转 Long
     *
     * @param localDateTime
     * @return 13位（毫秒）
     */
    public static long parseLong(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * LocalDate 转 Long
     *
     * @param localDate
     * @return 13位（毫秒）最后3位为0
     */
    public static long parseLong(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay(zone).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * 格式化时间 <br/>
     * 格式 yyyy-MM-dd HH:mm:ss
     *
     * @param dateTime
     * @return
     */
    public static String format(LocalDate dateTime) {
        return format(dateTime, null);
    }

    /**
     * 格式化时间
     *
     * @param dateTime
     * @param pattern  格式 默认:yyyy-MM-dd
     * @return
     */
    public static String format(LocalDate dateTime, String pattern) {
        if (StrUtil.isEmpty(pattern)) {
            pattern = DATE_PATTERN;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(dateTime);
    }

    /**
     * 格式化时间 <br/>
     * 格式 yyyy-MM-dd HH:mm:ss
     *
     * @param dateTime
     * @return
     */
    public static String format(LocalDateTime dateTime) {
        return format(dateTime, null);
    }

    /**
     * 格式化时间
     *
     * @param dateTime
     * @param pattern  格式 默认:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        if (StrUtil.isEmpty(pattern)) {
            pattern = DATE_TIME_PATTERN;
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return df.format(dateTime);
    }


    /** public static void main(String[] args) {
     SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     System.out.println(df.format(zonedDateTime2Date(ZonedDateTime.now())));
     System.out.println(df2.format(date2ZonedDateTime(new Date())));

     System.out.println(getWeek4First());
     System.out.println(format(getWeek4First(LocalDate.parse("2018-02-10")).atTime(LocalTime.MIN)));
     System.out.println(format(getWeek4Last(LocalDate.parse("2018-02-10")).atTime(LocalTime.MAX)));

     System.out.println(format(getMonth4First().atTime(LocalTime.MIN)));
     System.out.println(format(getMonth4Last().atTime(LocalTime.MAX)));

     System.out.println(format(getYear4First().atTime(LocalTime.MIN)));
     System.out.println(format(getYear4Last().atTime(LocalTime.MAX)));

     }*/


}
