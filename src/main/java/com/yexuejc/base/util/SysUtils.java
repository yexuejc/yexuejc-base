package com.yexuejc.base.util;

import java.net.URL;

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

    /**
     * 从根路径获取文件URL
     *
     * @param clazz
     * @return
     */
    public static URL getRootPath(Class clazz, String filePath) {
        return clazz.getClass().getResource(StrUtil.setStr(filePath, "/"));
    }

}
