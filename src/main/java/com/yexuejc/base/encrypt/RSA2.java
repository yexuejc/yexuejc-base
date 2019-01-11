package com.yexuejc.base.encrypt;

import com.yexuejc.base.util.StrUtil;

import java.io.*;
import java.security.*;
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

    /**
     * 证书格式转换 JKS(xx.keystore) 转 PKCS12(xx.pfx)
     *
     * @param inPath  证书输入文件路径
     * @param outPath 证书输出文件路径
     * @param oPwd    原证书密码
     * @param nPwd    新证书密码（为空同原证书密码一致）
     */
    public static void cover2Pfx(String inPath, String outPath, String oPwd, String nPwd) {
        try {
            FileInputStream fis = new FileInputStream(inPath);
            FileOutputStream out = new FileOutputStream(outPath);
            if (nPwd == null) {
                nPwd = oPwd;
            }
            cover2Pfx(fis, out, oPwd.toCharArray(), nPwd.toCharArray());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 证书格式转换 JKS(xx.keystore) 转 PKCS12(xx.pfx)
     *
     * @param fis  证书输入文件流
     * @param out  证书输出文件流[自行关闭->out.close()]
     * @param oPwd 原证书密码
     * @param nPwd 新证书密码（为空同原证书密码一致）
     */
    public static void cover2Pfx(FileInputStream fis, FileOutputStream out, char[] oPwd, char[] nPwd) {
        try {
            KeyStore inputKeyStore = KeyStore.getInstance("JKS");
            cover(fis, out, oPwd, nPwd, inputKeyStore, "PKCS12");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 证书格式转换 PKCS12(xx.pfx)  转  JKS(xx.keystore)
     *
     * @param inPath  证书输入文件路径
     * @param outPath 证书输出文件路径
     * @param oPwd    原证书密码
     * @param nPwd    新证书密码（为空同原证书密码一致）
     */
    public static void cover2keyStore(String inPath, String outPath, String oPwd, String nPwd) {
        try {
            FileInputStream fis = new FileInputStream(inPath);
            FileOutputStream out = new FileOutputStream(outPath);
            if (nPwd == null) {
                nPwd = oPwd;
            }
            cover2keyStore(fis, out, oPwd.toCharArray(), nPwd.toCharArray());
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 证书格式转换 PKCS12(xx.pfx)  转  JKS(xx.keystore)
     *
     * @param fis  证书输入文件流
     * @param out  证书输出文件流[自行关闭->out.close()]
     * @param oPwd 原证书密码
     * @param nPwd 新证书密码（为空同原证书密码一致）
     */
    public static void cover2keyStore(FileInputStream fis, FileOutputStream out, char[] oPwd, char[] nPwd) {
        try {
            KeyStore inputKeyStore = KeyStore.getInstance("PKCS12");
            cover(fis, out, oPwd, nPwd, inputKeyStore, "JKS");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 证书转换操作
     *
     * @param fis           证书输入文件流
     * @param out           证书输出文件流[自行关闭->out.close()]
     * @param oPwd          原证书密码
     * @param nPwd          新证书密码（为空同原证书密码一致）
     * @param inputKeyStore 输入格式
     * @param type          目标类型
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     */
    public static void cover(FileInputStream fis, FileOutputStream out, char[] oPwd, char[] nPwd, KeyStore inputKeyStore, String type) throws IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, UnrecoverableKeyException {
        inputKeyStore.load(fis, oPwd);
        fis.close();
        if (nPwd == null) {
            nPwd = oPwd;
        }
        KeyStore outputKeyStore = KeyStore.getInstance(type);
        outputKeyStore.load(null, nPwd);
        Enumeration<String> enums = inputKeyStore.aliases();
        while (enums.hasMoreElements()) {
            String keyAlias = enums.nextElement();
            System.out.println("alias=[" + keyAlias + "]");
            if (inputKeyStore.isKeyEntry(keyAlias)) {
                Key key = inputKeyStore.getKey(keyAlias, oPwd);
                Certificate[] certChain = inputKeyStore.getCertificateChain(keyAlias);
                outputKeyStore.setKeyEntry(keyAlias, key, nPwd, certChain);
            }
        }
        outputKeyStore.store(out, nPwd);
    }

    public static void main(String[] args) {
        cover2Pfx("D:\\mykeystore.keystore", "D:\\m1.pfx", "123456", null);
    }
}
