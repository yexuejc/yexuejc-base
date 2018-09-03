package com.yexuejc.base.util;


import sun.misc.BASE64Decoder;

/**
 * 算法工具类
 *
 * @ClassName: AlgorithmUtils
 * @Description:算法工具类
 * @author: maxf
 * @date: 2017年11月23日 下午3:17:58
 */
public class AlgorithmUtils {
    private static final int LENGTH_1 = 1;
    private static final int LENGTH_2 = 2;
    private static final int LENGTH_3 = 3;
    private static final int LENGTH_36 = 36;

    private AlgorithmUtils() {
    }

    private static final String X36 = "0123456789abcdefghijklmnopqrstuvwxyz";
    @SuppressWarnings("unused")
    private static final String X62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 生成下一个3位数36进制code
     *
     * @param subNum 当前code序数
     * @return
     * @throws NextCodeException String 下个序数的code
     * @Title: getNextCode
     * @Description: 生成下一个3位数36进制code
     * @throw
     */
    public static String getNextCode(int subNum) throws NextCodeException {
        String nextCode = x10ConvertTo36(subNum);
        if (nextCode.length() < LENGTH_1) {
            nextCode = "000";
        } else if (nextCode.length() == LENGTH_1) {
            nextCode = "00" + nextCode;
        } else if (nextCode.length() == LENGTH_2) {
            nextCode = "0" + nextCode;
        } else if (nextCode.length() > LENGTH_3) {
            throw new NextCodeException();
        }
        return nextCode;
    }

    /**
     * 10进制转换成36进制
     *
     * @param val 10进制
     * @return String 36进制
     * @Title: x10ConvertTo36
     * @Description: 10进制转换成36进制
     * @throw
     */
    public static String x10ConvertTo36(int val) {
        String result = "";
        while (val >= LENGTH_36) {
            result = X36.toCharArray()[val % 36] + result;
            val /= 36;
        }
        if (val >= 0) {
            result = X36.toCharArray()[val] + result;
        }
        return result;
    }

    /**
     * 36进制转10进制
     *
     * @param pStr 36进制
     * @return int 10进制
     * @Title: x36ConvertTo10
     * @Description: 36进制转10进制
     * @throw
     */
    public static int x36ConvertTo10(String pStr) {
        if (pStr == "") {
            return 0;
        }
        // 目标十进制数初始化为0
        int deciaml = 0;
        // 记录次方,初始为36进制长度 -1
        int power = pStr.length() - 1;
        // 将36进制字符串转换成char[]
        char[] keys = pStr.toCharArray();
        for (int i = 0; i < pStr.length(); i++) {
            // 拿到36进制对应的10进制数
            int value = X36.indexOf(keys[i]);
            deciaml = (int) (deciaml + value * Math.pow(X36.length(), power));
            // 执行完毕 次方自减
            power--;
        }
        return deciaml;
    }

    /**
     * 生成下一个3位数36进制code 异常
     *
     * @ClassName: NextCodeException
     * @Description: 生成下一个3位数36进制code 出现异常时抛出
     * @author: maxf
     * @date: 2017年11月22日 下午4:38:40
     */
    static class NextCodeException extends Exception {
        private static final long serialVersionUID = 8956943499144648985L;
    }

    /**
     * 16进制转为2进制
     *
     * @param hexString 16进制字符串
     */
    public static String x16ConvertTo2(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0) {
            return null;
        }
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    /**
     * 16进制转为2进制byte[]
     *
     * @param hexString 16进制字符串
     * @return
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

}
