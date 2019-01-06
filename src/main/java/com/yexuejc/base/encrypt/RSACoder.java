package com.yexuejc.base.encrypt;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA 加解密 工具模式
 *
 * @author maxf
 * @ClassName RSACoder
 * @Description
 * @date 2018/9/3 16:13
 */
public class RSACoder {

    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 解密<br>
     * 用公钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublic(String data, String key)
            throws Exception {
        return getDataByPublicKey(data, key, Cipher.DECRYPT_MODE);
    }

    /**
     * 解密<br>
     * 用私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(String data, String key)
            throws Exception {
        return getDataByPrivateKey(data, key, Cipher.DECRYPT_MODE);
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(String data, String key)
            throws Exception {
        return getDataByPublicKey(data, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 加密<br>
     * 用私钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(String data, String key)
            throws Exception {
        return getDataByPrivateKey(data, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 解密<br>
     * 用公钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublic(byte[] data, String key)
            throws Exception {
        return getDataByPublicKey(data, key, Cipher.DECRYPT_MODE);
    }

    /**
     * 解密<br>
     * 用私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String key)
            throws Exception {
        return getDataByPrivateKey(data, key, Cipher.DECRYPT_MODE);
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key)
            throws Exception {
        return getDataByPublicKey(data, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 加密<br>
     * 用私钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String key)
            throws Exception {
        return getDataByPrivateKey(data, key, Cipher.ENCRYPT_MODE);
    }

    /**
     * 通过公钥获得加解密数据
     *
     * @param data String
     * @param key  String
     * @param mode int
     * @return
     */
    public static byte[] getDataByPublicKey(String data, String key, int mode)
            throws Exception {
        return getDataByPublicKey(data.getBytes(), key, mode);
    }

    /**
     * 通过私钥获得加解密数据
     *
     * @param data String
     * @param key  String
     * @param mode 加密或解密
     * @return
     */
    public static byte[] getDataByPrivateKey(String data, String key, int mode)
            throws Exception {
        return getDataByPrivateKey(data.getBytes(), key, mode);
    }

    /**
     * 通过公钥获得加解密数据
     *
     * @param data String
     * @param key  String
     * @param mode int
     * @return
     */
    public static byte[] getDataByPublicKey(byte[] data, String key, int mode)
            throws Exception {
        // 取得私钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(key.getBytes("UTF-8")));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据进行加密或解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(mode, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 通过私钥获得加解密数据
     *
     * @param data String
     * @param key  String
     * @param mode 加密或解密
     * @return
     */
    public static byte[] getDataByPrivateKey(byte[] data, String key, int mode)
            throws Exception {
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(key.getBytes("UTF-8")));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(mode, privateKey);
        return cipher.doFinal(data);
    }
}
