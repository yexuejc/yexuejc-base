package com.yexuejc.base.util;

import sun.misc.BASE64Encoder;

import java.io.*;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;
import java.util.zip.CRC32;

/**
 * 文件工具类
 *
 * @author maxf:yexue
 * @className FileUtil
 * @description 工具类
 * @time 2017年11月3日 下午3:12:49
 */
public class FileUtil {
    static Logger logger = Logger.getLogger(FileUtil.class.getName());

    private FileUtil() {
    }

    private static final String TYPE_TAR_GZ = ".tar.gz";
    private static final String TAR_GZ = "tar.gz";

    /**
     * 获取文件类型：不适合所有
     * <p>
     * 根据文件名称截取.后的文件格式
     * </p>
     *
     * @param fileName
     * @return
     */
    public static String getFileType(String fileName) {
        try {
            if (fileName.lastIndexOf(TYPE_TAR_GZ) > 0) {
                return TAR_GZ;
            }
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } catch (Exception e) {
            logger.severe("file doesn't exist or is not a file");
        }
        return null;
    }

    /**
     * 判断文件是否存在
     *
     * @param file
     */
    public static void judeFileExists(File file) {

        if (file.exists()) {
            logger.severe("file exists");
        } else {
            logger.info("file not exists, create it ...");
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.severe("file create fail");
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
     *
     * @return 创建成功、失败
     */
    public static boolean judeDirExists(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                logger.severe("dir exists");
            } else {
                logger.severe("the same name file exists, can not create dir");
            }
        } else {
            logger.info("dir not exists, create it ...");
            return file.mkdirs();
        }
        return false;
    }

    /**
     * 获取文件sha1
     *
     * @param file
     * @return
     */
    public static String sha1(File file) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] buffer = new byte[1024 * 1024 * 10];

            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                digest.update(buffer, 0, len);
            }
            String sha1 = new BigInteger(1, digest.digest()).toString(16);
            int length = 40 - sha1.length();
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    sha1 = "0" + sha1;
                }
            }
            return sha1;
        } catch (NoSuchAlgorithmException e) {
            logger.severe("system algorithm error.");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            logger.severe("file doesn't exist or is not a file");
            e.printStackTrace();
        } catch (IOException e) {
            logger.severe("The operation file is an IO exception.");
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                logger.severe("close FileInputStream IO exception.");
            }
        }
        return null;
    }


    /***
     * 计算SHA1码
     *
     * @return String 适用于上G大的文件
     * @throws NoSuchAlgorithmException
     * */
    public static String sha1ByBigFile(File file) {
        MessageDigest messagedigest = null;
        try {
            messagedigest = MessageDigest.getInstance("SHA-1");
            FileInputStream in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            messagedigest.update(byteBuffer);
            return StrUtil.toHex(messagedigest.digest());
        } catch (NoSuchAlgorithmException e) {
            logger.severe("system algorithm error.");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            logger.severe("file doesn't exist or is not a file");
            e.printStackTrace();
        } catch (IOException e) {
            logger.severe("The operation file is an IO exception.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 文件md5
     *
     * @param file
     * @return
     */
    public static String md5(File file) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[1024 * 1024 * 10];

            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                digest.update(buffer, 0, len);
            }
            String md5 = new BigInteger(1, digest.digest()).toString(16);
            int length = 32 - md5.length();
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    md5 = "0" + md5;
                }
            }
            return md5;
        } catch (IOException e) {
            logger.severe("The operation file is an IO exception.");
        } catch (NoSuchAlgorithmException e) {
            logger.severe("system algorithm error.");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                logger.severe("close FileInputStream IO exception.");
            }
        }
        return null;
    }

    /**
     * 对一个文件获取md5值
     *
     * @return md5串
     * @throws NoSuchAlgorithmException
     */
    public static String md5ByBigFile(File file) {

        MessageDigest messagedigest = null;
        try {
            messagedigest = MessageDigest.getInstance("MD5");
            FileInputStream in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,
                    file.length());
            messagedigest.update(byteBuffer);
            return StrUtil.toHex(messagedigest.digest());
        } catch (NoSuchAlgorithmException e) {
            logger.severe("system algorithm error.");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            logger.severe("file doesn't exist or is not a file");
            e.printStackTrace();
        } catch (IOException e) {
            logger.severe("The operation file is an IO exception.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取文件CRC32码
     *
     * @return String
     */
    public static String crc32(File file) {
        CRC32 crc32 = new CRC32();
        // MessageDigest.get
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                crc32.update(buffer, 0, length);
            }
            return crc32.getValue() + "";
        } catch (FileNotFoundException e) {
            logger.severe("file doesn't exist or is not a file");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            logger.severe("The operation file is an IO exception.");
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                logger.severe("close FileInputStream IO exception.");
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文件base64
     *
     * @param file
     * @return
     */
    public static String base64(File file) {
        FileInputStream fileInputStream = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            fileInputStream = new FileInputStream(file);
            data = new byte[fileInputStream.available()];
            fileInputStream.read(data);
            fileInputStream.close();
        } catch (IOException e) {
            logger.severe("The operation file is an IO exception.");
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * base64转文件
     * <p>
     * <i>
     * 文件转base64请使用 {@link FileUtil#base64(File)}
     * </i>
     *
     * @param decode   baseByte
     * @param fileName 文件名称（包含路径）
     * @return 返回保存地址
     */
    public static String base64ToFile(byte[] decode, String fileName) {

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(fileName);
            out.write(decode);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    /**
     * 获取文件大小 ：直接返回大小
     *
     * @param f
     * @return f.length()
     */
    public static long size(File f) {
        if (f.exists() && f.isFile()) {
            return f.length();
        } else {
            logger.info("file doesn't exist or is not a file");
        }
        return 0;
    }

    /**
     * 获取文件大小 : 用流的方式获取
     *
     * @param f
     * @return
     */
    public static long size4Stream(File f) {
        FileChannel fc = null;
        try {
            if (f.exists() && f.isFile()) {
                FileInputStream fis = new FileInputStream(f);
                fc = fis.getChannel();
                return fc.size();
            } else {
                logger.info("file doesn't exist or is not a file");
            }
        } catch (FileNotFoundException e) {
            logger.severe("file doesn't exist or is not a file");
        } catch (IOException e) {
            logger.severe("The operation file is an IO exception.");
        } finally {
            if (null != fc) {
                try {
                    fc.close();
                } catch (IOException e) {
                    logger.severe("close FileInputStream IO exception.");
                }
            }
        }
        return 0;
    }

    /*public static void main(String[] args) {
        long size = FileUtil.size(new File("E:\\OS\\deepin-15.6-amd64\\DeepinCloudPrintServerInstaller_1.0.0.1.exe"));
        System.out.println(size);
        System.out.println(1024 * 1024 * 5);
        if (size > 1024 * 1024 * 5) {
            System.out.println("文件最大5M");
            return;
        }

        long s1 = fileSize(new File("E:\\OS\\cn_windows_10_consumer_editions_version_1803_updated_march_2018_x64_dvd_12063766.iso"));
        System.out.println(s1);
        long s2 = fileSize4Stream(new File("E:\\OS\\cn_windows_10_consumer_editions_version_1803_updated_march_2018_x64_dvd_12063766.iso"));
        System.out.println(s2);

        String s1 = base64(new File("C:\\Users\\Administrator\\Desktop\\a.html"));
        System.out.println(s1);

        String s = sha1(new File("C:\\Users\\Administrator\\Desktop\\a.html"));
        String s2 = sha1ByBigFile(new File("C:\\Users\\Administrator\\Desktop\\a.html"));
        System.out.println(s);
        System.out.println(s2);


        String md5 = md5(new File("C:\\Users\\Administrator\\Desktop\\a.html"));
        String md52 = md5ByBigFile(new File("C:\\Users\\Administrator\\Desktop\\a.html"));
        System.out.println(md5);
        System.out.println(md52);


        String crc32 = crc32(new File("C:\\Users\\Administrator\\Desktop\\a.html"));
        System.out.println(crc32);
    }*/
}
