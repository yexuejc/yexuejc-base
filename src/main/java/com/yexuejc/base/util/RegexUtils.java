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
public class RegexUtils {
    private RegexUtils() {
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
