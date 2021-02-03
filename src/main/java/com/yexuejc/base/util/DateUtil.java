package com.yexuejc.base.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * java.util.Date 时间工具类
 *
 * @author maxf
 * @ClassName DateUtil
 * @Description
 * @date 2018/9/3 15:27
 */
public class DateUtil {
    private DateUtil() {
    }

    public static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    public static DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public static String DAY_START_TIME = "00:00:00";
    public static String DAY_END_TIME = "23:59:59";

    /**
     * 取得服务器当前日期
     *
     * @return 当前日期：格式（yyyy-MM-dd）
     */
    static public String currentDate() {
        Date curDate = new Date();
        return DATE_FORMAT.format(curDate);
    }

    /**
     * 取得服务器当前时间
     *
     * @return 当前日期：格式（hh:mm:ss）
     */
    static public String currentTime() {
        Date curDate = new Date();
        return TIME_FORMAT.format(curDate);
    }

    /**
     * 取得服务器当前日期和时间
     *
     * @return 当前日期：格式（yyyy-MM-dd hh:mm:ss）
     */
    static public String currentDateTime() {
        Date curDate = new Date();
        return DATE_TIME_FORMAT.format(curDate);
    }

    /**
     * 比较两个日期大小
     *
     * @param date1
     * @param date2
     * @return date1>date2返回1;date1=date2返回0;date1<date2返回-1
     * @throws ParseException
     */
    public static byte dateCompare(String date1, String date2) throws ParseException {

        Date d1 = DATE_FORMAT.parse(date1);
        Date d2 = DATE_FORMAT.parse(date2);
        if (d1.before(d2)) {
            return -1;
        } else if (d1.equals(d2)) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 日期字符串转date
     *
     * @param dateStr
     * @return Date
     * @throws ParseException
     */
    public static Date str2date(String dateStr) throws ParseException {
        Date date = DATE_FORMAT.parse(dateStr);
        return date;
    }

    /**
     * date转字符串
     *
     * @param date
     * @return Date
     * @throws ParseException
     */
    public static String date2str(Date date) throws ParseException {
        if (date != null) {
            return DATE_FORMAT.format(date);
        } else {
            return "null";
        }
    }

    /**
     * 日期字符串转dateTime
     *
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date str2dateTime(String dateStr) throws ParseException {
        Date date = DATE_TIME_FORMAT.parse(dateStr);
        return date;
    }

    /**
     * dateTime转字符串
     *
     * @param date
     * @return Date
     * @throws ParseException
     */
    public static String dateTime2str(Date date) throws ParseException {
        if (date != null) {
            return DATE_TIME_FORMAT.format(date);
        } else {
            return "null";
        }
    }


    /**
     * 获取本周的日期
     *
     * @param dayOfWeek :可用值:Calendar.SUNDAY,Calendar.MONDAY,
     *                  Calendar.TUESDAY,Calendar.WEDNESDAY,Calendar.THURSDAY,
     *                  Calendar.FRIDAY,Calendar.SATURDAY
     * @return
     */
    public static String getCurrentWeek(int dayOfWeek) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        if (Calendar.SUNDAY == dayOfWeek) {
            calendar.add(Calendar.WEEK_OF_MONTH, 1);
        }
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return DATE_FORMAT.format(calendar.getTime());
    }

    /**
     * 两个日期相减
     *
     * @param date1
     * @param date2
     * @param flag  : 'y':得出相差年数,'M':相差月数,'d':相差天数,'h':相差小时数
     *              'm':相差分钟数,'s':相差秒数,其他:相差毫秒数
     * @return
     */
    public static long dateMinus(Date date1, Date date2, char flag) {
        long msMinus = date1.getTime() - date2.getTime();
        switch (flag) {
            case 'y':
                return msMinus / (365L * 24L * 60L * 60L * 1000L);
            case 'M':
                return msMinus / (30L * 24L * 60L * 60L * 1000L);
            case 'd':
                return msMinus / (24L * 60L * 60L * 1000L);
            case 'h':
                return msMinus / (60L * 60L * 1000L);
            case 'm':
                return msMinus / (60L * 1000L);
            case 's':
                return msMinus / 1000L;
            default:
                return msMinus;
        }
    }

    /**
     * 一个日期加上xx天,返回加法之后的日期
     *
     * @param date
     * @param days
     * @return
     */
    public static Date datePlus(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + days);
        return calendar.getTime();
    }

}
