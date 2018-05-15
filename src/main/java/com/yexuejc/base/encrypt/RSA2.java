package com.yexuejc.base.encrypt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * RSA加解密 证书模式
 * 依赖 {@link RSA}
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
     * 得到私钥
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
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(new FileInputStream(filepath), password.toCharArray());
        return (RSAPrivateKey) ks.getKey(alias, password.toCharArray());
    }

}
