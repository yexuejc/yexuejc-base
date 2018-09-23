package com.yexuejc.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则验证
 *
 * @author yexue
 * @expl
 * @time 2017年11月9日 上午11:01:24
 */
public class RegexUtil {
    private RegexUtil() {
    }

    /**
     * 正则规则:小写字母
     */
    public static final String REGEX_LOWERCASE = "^$|^[a-z\\d_]+$";
    /**
     * 正则规则:中国人姓名
     * 例：张三、买买提·也罗伊
     */
    public static final String CHINA_NAME = "^[\\u4e00-\\u9fa5]+(·[\\u4e00-\\u9fa5]+)*$";
    ;
    /**
     * 短信验证码：4位数字
     */
    public static final String REGEX_NUM4 = "^$|^\\d{4}$";
    /**
     * cvn2：3位数字
     */
    public static final String REGEX_NUM3 = "^$|^\\d{3}$";
    /**
     * ID用 正则表达式(32位 16进制小写编码)
     */
    public static final String REGEX_ID = "^$|^[a-f0-9]{32}$";
    /**
     * 手机用 正则表达式(首位为1,共11位数字)
     */
    public static final String REGEX_MOBILE = "^$|^1\\d{10}$";
    /**
     * 验证是否是Json数据 正则表达式(首尾是{})
     */
    public static final String REGEX_JSON = "^$|^\\{.*\\}$";
    /**
     * 验证预约日期时间 正则表达式(精确到半小时)
     */
    public static final String REGEX_DATE_APPOINTMENT = "^$|^\\d{4}-[01]\\d-[0-3]\\d [0-2]\\d:(0|3)0$";
    /**
     * 日期 正则表达式
     */
    public static final String REGEX_DATE = "^$|^\\d{4}-[01]\\d-[0-3]\\d$";

    /**
     * 6位数字
     */
    public static final String REGEX_NUM6 = "^$|^\\d{6}$";
    /**
     * 两位以内正整数
     */
    public static final String REGEX_PINT2 = "^$|^[1-9]\\d{0,1}$";
    /**
     * 五位以内正整数
     */
    public static final String REGEX_PINT5 = "^$|^[1-9]\\d{0,4}$";
    /**
     * 十位以内正整数
     */
    public static final String REGEX_PINT10 = "^$|^[1-9]\\d{0,9}$";
    /**
     * 十位以内正整或0
     */
    public static final String REGEX_INT10 = "^$|^[1-9]\\d{0,9}$|^0$";
    /**
     * 可有8位整数,2位小数
     */
    public static final String REGEX_PFLOAT10_2 = "^$|^(\\d\\.\\d{1})|([1-9]\\d{0,7}(\\.\\d{1,2})?)$";

    /**
     * STS RoleSessionName
     */
    public static final String REGEX_STS_ROLE_SESSION_NAME = "^[a-zA-Z0-9\\.@\\-_]+$";

    /**
     * 正则：入参验证
     *
     * @param parms
     * @param regex
     * @return
     */
    public static boolean regex(String parms, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(parms);
        return matcher.matches();
    }

}
