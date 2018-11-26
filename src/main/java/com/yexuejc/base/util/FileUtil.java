package com.yexuejc.base.util;

import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    public static String getFileType(String fileName) throws FileNotFoundException {
        try {
            if (fileName.lastIndexOf(TYPE_TAR_GZ) > 0) {
                return TAR_GZ;
            }
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } catch (Exception e) {
            throw new FileNotFoundException("文件类型未能解析");
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param file
     */
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
     *
     * @return 创建成功、失败
     */
    public static boolean judeDirExists(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                System.out.println("dir exists");
            } else {
                System.out.println("the same name file exists, can not create dir");
            }
        } else {
            System.out.println("dir not exists, create it ...");
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
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
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
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
            System.out.println(e);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                System.out.println(e);
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
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
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
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }


   /* public static void main(String[] args) {

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
