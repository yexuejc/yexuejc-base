package com.yexuejc.base.encrypt;


import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;


/**
 * RSA加解密 配置模式
 *
 * @ClassName: RSA
 * @Description:
 * @author: maxf
 * @date: 2018/5/15 14:39
 */
public class RSA {

    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA";
    /**
     * 加密方式
     * <pre>
     *     RSA 可选择isChangeSign 是否每次改变加密结果
     *     RSA/None/NoPadding 不改变加密结果
     *     RSA/ECB/PKCS1Padding 改变加密结果
     * </pre>
     */
    public static String RSA_ALGORITHM_ECB = "RSA";
    /**
     * 是否每次改变加密结果
     * 只针对于RSA_ALGORITHM_ECB = "RSA"有效
     */
    public static boolean isChangeSign = true;
    /**
     * 是否使用 Base64URL 方式加密 默认正常加密
     * <pre>
     *     关于 Base64URL 和正常加密的区别：Base64URL会把 '+', '/' 转换成 '-', '_' 来防止请求时url上的转义
     *     private static final byte[] STANDARD_ENCODE_TABLE = {
     *             'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
     *             'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
     *             'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
     *             'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
     *             '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
     *     };
     *      private static final byte[] URL_SAFE_ENCODE_TABLE = {
     *             'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
     *             'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
     *             'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
     *             'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
     *             '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
     *     };
     * </pre>
     */
    public static boolean encodeBase64URLSafe = false;
    /**
     * 签名算法
     */
    public static SignAlgorithm signAlgorithm = SignAlgorithm.SHA1withRSA;


    /**
     * 生成密钥对
     *
     * @param keySize       生成长度
     * @param base64URLSafe 是否生成 base64URL 格式的密钥：默认false
     * @return
     */
    public static Map<String, String> initKeys(int keySize, boolean base64URLSafe) {
        encodeBase64URLSafe = base64URLSafe;
        return initKeys(keySize);
    }

    /**
     * 生成密钥对
     *
     * @param keySize 生成长度
     * @return
     */
    public static Map<String, String> initKeys(int keySize) {
        //为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }

        //初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        //生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        //得到公钥
        Key publicKey = keyPair.getPublic();
        //得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = null;
        String publicKeyStr = null;
        if (encodeBase64URLSafe) {
            publicKeyStr = Base64.encodeBase64URLSafeString(publicKey.getEncoded());
            privateKeyStr = Base64.encodeBase64URLSafeString(privateKey.getEncoded());
        } else {
            publicKeyStr = Base64.encodeBase64String(publicKey.getEncoded());
            privateKeyStr = Base64.encodeBase64String(privateKey.getEncoded());
        }
        Map<String, String> keyPairMap = new HashMap<String, String>(2);
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);

        return keyPairMap;
    }


    /**
     * 得到公钥
     *
     * @param publicKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }

    /**
     * 得到私钥
     *
     * @param privateKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        //通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }

    /**
     * 公钥加密
     *
     * @param data          加密原串数据
     * @param publicKey     公钥
     * @param base64URLSafe 是否生成 base64URL 格式的密钥：默认false
     * @return
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey, boolean base64URLSafe) {
        encodeBase64URLSafe = base64URLSafe;
        return publicEncrypt(data, publicKey);
    }

    /**
     * 公钥加密
     *
     * @param data      加密原串数据
     * @param publicKey 公钥
     * @return
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = getCipher();
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            if (encodeBase64URLSafe) {
                return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength()));
            } else {
                return Base64.encodeBase64String(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength()));
            }
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     */
    public static String privateDecrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = getCipher();
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), privateKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }


    /**
     * 私钥加密
     *
     * @param data          加密原串数据
     * @param privateKey    公钥
     * @param base64URLSafe 是否生成 base64URL 格式的密钥：默认false
     * @return
     */
    public static String privateEncrypt(String data, RSAPrivateKey privateKey, boolean base64URLSafe) {
        encodeBase64URLSafe = base64URLSafe;
        return privateEncrypt(data, privateKey);
    }

    /**
     * 私钥加密
     *
     * @param data       加密原串数据
     * @param privateKey 公钥
     * @return
     */
    public static String privateEncrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = getCipher();
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            if (encodeBase64URLSafe) {
                return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), privateKey.getModulus().bitLength()));
            } else {
                return Base64.encodeBase64String(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), privateKey.getModulus().bitLength()));
            }
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 公钥解密
     *
     * @param data
     * @param publicKey
     * @return
     */

    public static String publicDecrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = getCipher();
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data), publicKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 获取 Cipher
     *
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    private static Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        Cipher cipher;
        if ("RSA".equals(RSA_ALGORITHM_ECB) && isChangeSign) {
            cipher = Cipher.getInstance(RSA_ALGORITHM_ECB);
        } else {
            Security.addProvider(new BouncyCastleProvider());
            cipher = Cipher.getInstance(RSA_ALGORITHM_ECB, "BC");
        }
        return cipher;
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        try {
            out.close();
        } catch (IOException e) {
        }
        return resultDatas;
    }

    private static Signature signature;


    /**
     * /**
     * 私钥签名：默认算法SHA1withRSA
     * <p>
     * 签名算法 {@link SignAlgorithm}
     * </p>
     *
     * @param plaintext     签名字符串
     * @param privateKey    签名私钥
     * @param base64URLSafe 是否生成 base64URL 格式的密钥：默认false
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String sign(String plaintext, RSAPrivateKey privateKey, boolean base64URLSafe) throws NoSuchAlgorithmException {
        encodeBase64URLSafe = base64URLSafe;
        return sign(plaintext, privateKey);
    }

    /**
     * 私钥签名：默认算法SHA1withRSA
     * <p>
     * 签名算法 {@link SignAlgorithm}
     * </p>
     *
     * @param plaintext  签名字符串
     * @param privateKey 签名私钥
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String sign(String plaintext, RSAPrivateKey privateKey) throws NoSuchAlgorithmException {
        signature = Signature.getInstance(signAlgorithm.getValue());
        String signBase64Str = "";

        try {
            signature.initSign(privateKey);
            try {
                signature.update(plaintext.getBytes(CHARSET));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new RuntimeException("签名字符串[" + plaintext + "]的数据时发生异常", e);
            }
            if (encodeBase64URLSafe) {
                signBase64Str = Base64.encodeBase64URLSafeString(signature.sign());
            } else {
                signBase64Str = Base64.encodeBase64String(signature.sign());
            }
            return signBase64Str;
        } catch (InvalidKeyException var6) {
            var6.printStackTrace();
            throw new RuntimeException("签名字符串[" + plaintext + "]的数据时发生异常", var6);
        } catch (SignatureException var7) {
            var7.printStackTrace();
            throw new RuntimeException("签名字符串[" + plaintext + "]的数据时发生异常", var7);
        }
    }

    /**
     * 公钥校验签名
     *
     * @param plaintext 原串
     * @param signStr   签名串
     * @param publicKey 公钥
     * @return
     * @throws UnsupportedEncodingException
     */
    public static boolean verify(String plaintext, String signStr, RSAPublicKey publicKey) throws UnsupportedEncodingException {
        boolean isValid = false;
        try {
            signature.initVerify(publicKey);
            signature.update(plaintext.getBytes(CHARSET));
            isValid = signature.verify(Base64.decodeBase64(signStr));
        } catch (InvalidKeyException var6) {
            var6.printStackTrace();
            throw new RuntimeException("校验签名字符串[" + plaintext + "]的数据时发生异常", var6);
        } catch (SignatureException var7) {
            var7.printStackTrace();
            throw new RuntimeException("校验签名字符串[" + plaintext + "]的数据时发生异常", var7);
        }

        return isValid;
    }
}
