package com.yexuejc.base.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;


/**
 * 3DES加解密
 *
 * @author maxf
 * @ClassName ThreeDES
 * @Description
 * @date 2018/9/3 17:09
 */
public class ThreeDES {

    private static final String IV = "1234567-";
    private final static String encoding = "utf-8";

    /**
     * DESCBC加密
     *
     * @param src 数据源
     * @param key 密钥
     * @return 返回加密后的数据
     * @throws Exception
     */
    public static String encryptDESCBC(final String src, final String key) throws Exception {
        if (key.length() < 24) {
            throw new InvalidKeyException("key的length不得小于24");
        }
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);

        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] encryptData = cipher.doFinal(src.getBytes(encoding));
        return Base64.encodeBase64URLSafeString(encryptData);
    }

    /**
     * DESCBC解密
     *
     * @param src 数据源
     * @param key 密钥
     * @return 返回解密后的原始数据
     * @throws Exception
     */
    public static String decryptDESCBC(final String src, final String key) throws Exception {
        if (key.length() < 24) {
            throw new InvalidKeyException("key的length不得小于24");
        }
        Key deskey = null;
        DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

        byte[] decryptData = cipher.doFinal(Base64.decodeBase64(src));

        return new String(decryptData, encoding);
    }

    /**
     * 填充，不是8的倍数会填充成8的倍数
     *
     * @param str
     * @return
     */
    public static String padding(String str) {
        byte[] oldByteArray;
        try {
            oldByteArray = str.getBytes("UTF8");
            int numberToPad = 8 - oldByteArray.length % 8;
            byte[] newByteArray = new byte[oldByteArray.length + numberToPad];
            System.arraycopy(oldByteArray, 0, newByteArray, 0,
                    oldByteArray.length);
            for (int i = oldByteArray.length; i < newByteArray.length; ++i) {
                newByteArray[i] = 0;
            }
            return new String(newByteArray, "UTF8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Crypter.padding UnsupportedEncodingException");
        }
        return null;
    }

}
	
