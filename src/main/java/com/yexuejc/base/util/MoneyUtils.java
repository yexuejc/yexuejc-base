package com.yexuejc.base.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * q钱相关工具类
 *
 * @ClassName: MoneyUtils
 * @Description:
 * @author: maxf
 * @date: 2017年12月1日 下午5:33:57
 */
public class MoneyUtils {
    private MoneyUtils() {
    }

    /**
     * 分转元
     *
     * @param num
     * @return String
     * @Title: formatPrice
     * @Description:分转元
     * @throw
     */
    public static String toYuan(Integer num) {
        if (num == null) {
            return "0.00";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        BigDecimal bigDecimal = new BigDecimal(num).divide(new BigDecimal(100));
        String str = df.format(bigDecimal);
        return str;
    }

    /**
     * 元转分
     *
     * @param num
     * @return int
     * @Title: formatPrice
     * @Description: 元转分
     * @throw
     */
    public static int toFen(String num) {
        if (num == null) {
            return 0;
        }
        DecimalFormat df = new DecimalFormat("#0");
        BigDecimal bigDecimal = new BigDecimal(num).multiply(new BigDecimal(100));
        String str = df.format(bigDecimal);
        return Integer.parseInt(str);
    }
}
