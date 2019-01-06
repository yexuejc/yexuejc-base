package com.yexuejc.base.encrypt;

import com.yexuejc.base.util.StrUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Enumeration;

/**
 * RSA加解密 证书模式
 * 依赖 {@link RSA}
 *
 * @ClassName: RSA2
 * @Description:
 * @author: maxf
 * @date: 2018/5/15 14:37
 */
public class RSA2 {

    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA";

    /**
     * 得到公钥
     *
     * @param filepath 密钥文件路径
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String filepath) throws CertificateException, FileNotFoundException {
        //通过证书,获取公钥
        CertificateFactory cf = null;
        cf = CertificateFactory.getInstance("X.509");
        Certificate c = cf.generateCertificate(new FileInputStream(filepath));
        return (RSAPublicKey) c.getPublicKey();
    }

    /**
     * 得到公钥
     *
     * @param pubKeyIn 密钥文件流
     * @return
     * @throws CertificateException
     */
    public static RSAPublicKey getPublicKey(InputStream pubKeyIn) throws CertificateException {
        //通过证书,获取公钥
        CertificateFactory cf = null;
        cf = CertificateFactory.getInstance("X.509");
        Certificate c = cf.generateCertificate(pubKeyIn);
        return (RSAPublicKey) c.getPublicKey();
    }

    /**
     * 读取JKS格式的key（私钥）keystore格式
     *
     * @param filepath 私钥路径
     * @param alias    证书别名
     * @param password 证书密码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws IOException
     * @throws CertificateException
     * @throws UnrecoverableKeyException
     */
    public static RSAPrivateKey getPrivateKey(String filepath, String alias, String password) throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException, UnrecoverableKeyException {
        return getPrivateKey(filepath, alias, password, "JKS");
    }

    /**
     * 读取JKS格式的key（私钥）keystore格式
     *
     * @param priKeyIn 私钥文件流
     * @param alias    证书别名
     * @param password 证书密码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws IOException
     * @throws CertificateException
     * @throws UnrecoverableKeyException
     */
    public static RSAPrivateKey getPrivateKey(InputStream priKeyIn, String alias, String password) throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException, UnrecoverableKeyException {
        return getPrivateKey(priKeyIn, alias, password, "JKS");
    }

    /**
     * 读取PKCS12格式的key（私钥）pfx格式
     *
     * @param filepath 私钥路径
     * @param alias    证书别名 可空
     * @param password 证书密码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws IOException
     * @throws CertificateException
     * @throws UnrecoverableKeyException
     */
    public static RSAPrivateKey getPrivateKeyFromPKCS12(String filepath, String alias, String password) throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException, UnrecoverableKeyException {
        return getPrivateKey(filepath, alias, password, "PKCS12");
    }

    /**
     * 读取PKCS12格式的key（私钥）pfx格式
     *
     * @param priKeyIn 私钥文件流
     * @param alias    证书别名 可空
     * @param password 证书密码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws IOException
     * @throws CertificateException
     * @throws UnrecoverableKeyException
     */
    public static RSAPrivateKey getPrivateKeyFromPKCS12(InputStream priKeyIn, String alias, String password) throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException, UnrecoverableKeyException {
        return getPrivateKey(priKeyIn, alias, password, "PKCS12");
    }

    /**
     * 读取key（私钥）
     *
     * @param filepath 私钥路径
     * @param alias    证书别名 可空
     * @param password 证书密码
     * @param type     证书格式
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws IOException
     * @throws CertificateException
     * @throws UnrecoverableKeyException
     */
    public static RSAPrivateKey getPrivateKey(String filepath, String alias, String password, String type) throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException, UnrecoverableKeyException {
        KeyStore ks = KeyStore.getInstance(type);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filepath);
            ks.load(fileInputStream, password.toCharArray());
            if (StrUtil.isEmpty(alias)) {
                Enumeration<?> aliases = ks.aliases();
                if (aliases != null) {
                    if (aliases.hasMoreElements()) {
                        alias = (String) aliases.nextElement();
                    }
                }
            }
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
        return (RSAPrivateKey) ks.getKey(alias, password.toCharArray());
    }

    /**
     * 读取key（私钥）
     *
     * @param priKeyIn 私钥文件流
     * @param alias    证书别名 可空
     * @param password 证书密码
     * @param type     证书格式
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws IOException
     * @throws CertificateException
     * @throws UnrecoverableKeyException
     */
    public static RSAPrivateKey getPrivateKey(InputStream priKeyIn, String alias, String password, String type) throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException, UnrecoverableKeyException {
        KeyStore ks = KeyStore.getInstance(type);
        ks.load(priKeyIn, password.toCharArray());
        if (StrUtil.isEmpty(alias)) {
            Enumeration<?> aliases = ks.aliases();
            if (aliases != null) {
                if (aliases.hasMoreElements()) {
                    alias = (String) aliases.nextElement();
                }
            }
        }
        return (RSAPrivateKey) ks.getKey(alias, password.toCharArray());
    }

}
