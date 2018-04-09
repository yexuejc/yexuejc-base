package com.yexuejc.base.util;

/**
 * 系统工具类
 *
 * @PackageName: com.yexuejc.util.base
 * @Description:
 * @author: maxf
 * @date: 2017/12/28 16:12
 */
public class SysUtils {
    private static final String PROJECT_ROOT_PATH = "java.io.tmpdir";

    private SysUtils() {
    }

    /**
     * 获取临时缓存路径
     *
     * @return String
     */
    public static String getCachePath() {
        return System.getProperty(PROJECT_ROOT_PATH);
    }
}
