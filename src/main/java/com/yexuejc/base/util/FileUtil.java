package com.yexuejc.base.util;

import java.io.File;
import java.io.IOException;

/**
 * @author maxf:yexue
 * @className FileUtil
 * @description 工具类
 * @time 2017年11月3日 下午3:12:49
 */
public class FileUtil {
    private FileUtil() {
    }

    private static final String TYPE_TAR_GZ = ".tar.gz";
    private static final String TAR_GZ = "tar.gz";

    public static String getFileType(String fileName) {
        if (fileName.lastIndexOf(TYPE_TAR_GZ) > 0) {
            return TAR_GZ;
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    // 判断文件是否存在
    public static void judeFileExists(File file) {

        if (file.exists()) {
            System.out.println("file exists");
        } else {
            System.out.println("file not exists, create it ...");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * <pre>
     * 判断文件夹是否存在, 不存在创建【采用mkdirs】
     * 相关解释：
     * 1、File类的createNewFile根据抽象路径创建一个新的空文件，当抽象路径制定的文件存在时，创建失败
     * 2、File类的mkdir方法根据抽象路径创建目录
     * 3、File类的mkdirs方法根据抽象路径创建目录，包括创建必需但不存在的父目录
     * 4、File类的createTempFile方法创建临时文件，可以制定临时文件的文件名前缀、后缀及文件所在的目录，如果不指定目录，则存放在系统的临时文件夹下。
     * 5、除mkdirs方法外，以上方法在创建文件和目录时，必须保证目标文件不存在，而且父目录存在，否则会创建失败
     * </pre>
     */
    public static void judeDirExists(File file) {

        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println("dir exists");
            } else {
                System.out.println("the same name file exists, can not create dir");
            }
        } else {
            System.out.println("dir not exists, create it ...");
            file.mkdirs();
        }

    }

}
