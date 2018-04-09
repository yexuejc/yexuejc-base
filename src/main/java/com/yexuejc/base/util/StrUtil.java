package com.yexuejc.base.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @PackageName: com.yexuejc.util.base.util
 * @Description:
 * @author: maxf
 * @date: 2017/12/28 17:34
 */
public final class StrUtil {
    public static char[] HEX_CHAR = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f'};

    private StrUtil() {
    }

    public static boolean isEmpty(Object obj) {
        if (obj instanceof String) {
            return obj == null || "".equals((String) obj);
        } else if (obj instanceof Object[]) {
            return obj == null || ((Object[]) ((Object[]) obj)).length == 0;
        } else if (obj instanceof Collection) {
            return obj == null || ((Collection) obj).size() == 0;
        } else {
            return obj == null;
        }
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static String genUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String genNum() {
        int hashCode = UUID.randomUUID().toString().hashCode();
        StringBuffer num = new StringBuffer();
        if (hashCode < 0) {
            hashCode = 0 - hashCode;
            num.append("0");
        } else {
            num.append("1");
        }

        return num.append(String.format("%010d", hashCode)).toString().substring(0, 8);
    }

    public static Map<String, String> parseUrlencoded(String urlencoded) {
        if (isEmpty(urlencoded)) {
            return null;
        } else {
            String[] entrys = urlencoded.split("&");
            if (isEmpty(entrys)) {
                return null;
            } else {
                Map<String, String> map = new HashMap();
                String[] kv = null;
                String[] var4 = entrys;
                int var5 = entrys.length;

                for (int var6 = 0; var6 < var5; ++var6) {
                    String entry = var4[var6];
                    if (!isEmpty(entry)) {
                        kv = entry.split("=");
                        if (!isEmpty(kv)) {
                            if (kv.length > 1) {
                                map.put(kv[0], kv[1]);
                            } else {
                                map.put(kv[0], null);
                            }
                        }
                    }
                }

                return map;
            }
        }
    }

    public static String toHex(byte[] buf) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);

        for (int i = 0; i < buf.length; ++i) {
            if ((buf[i] & 255) < 16) {
                strbuf.append("0");
            }

            strbuf.append(Long.toString((long) (buf[i] & 255), 16));
        }

        return strbuf.toString();
    }

    public static String toMD5(String str) {
        if (str == null) {
            return null;
        } else {
            MessageDigest md = null;

            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException var3) {
                var3.printStackTrace();
                return null;
            }

            md.update(str.getBytes());
            byte[] tmp = md.digest();
            return toHex(tmp);
        }
    }

    public static String iso2utf(String str) {
        String utfStr = null;

        try {
            utfStr = new String(str.getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
        }

        return utfStr;
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    public static String codeId(String id) {
        if (id != null && id.length() == 32) {
            StringBuilder coded = new StringBuilder();

            int i;
            for (i = 0; i < 13; ++i) {
                coded.append(HEX_CHAR[(int) (Math.random() * 15.0D) + 1]);
            }

            coded.append(id.substring(0, 11));

            for (i = 0; i < 7; ++i) {
                coded.append(HEX_CHAR[(int) (Math.random() * 15.0D) + 1]);
            }

            coded.append(id.substring(11));

            for (i = 0; i < 12; ++i) {
                coded.append(HEX_CHAR[(int) (Math.random() * 15.0D) + 1]);
            }

            return coded.toString();
        } else {
            return id;
        }
    }

    public static String decodeId(String coded) {
        if (coded != null && coded.length() == 64) {
            StringBuilder id = new StringBuilder();
            id.append(coded.substring(13, 24));
            id.append(coded.substring(31, 52));
            return id.toString();
        } else {
            return coded;
        }
    }


    /**
     * 替换手机号中间4位为*
     *
     * @param mobile
     * @return String
     * @Title: replaceMobile
     * @Description: 替换手机号中间4位为*
     * @throw
     */
    public static String replaceMobile(String mobile) {
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }
}
