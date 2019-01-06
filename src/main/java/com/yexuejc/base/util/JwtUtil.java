package com.yexuejc.base.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * jwt工具类
 * <p>
 * 升级2.0
 * <br/>
 * 由静态类分装成单例类，可配置参数config()
 * </p>
 *
 * @author maxf
 * @version 2.0
 * @ClassName JwtUtil
 * @Description
 * @date 2018/9/3 15:28
 */
public class JwtUtil {

    private JwtUtil() {
    }

    public static JwtUtil instace() {
        return Instace.jwtUtil;
    }

    /**
     * 参数配置：设置一次即可，多次设置会覆盖之前的
     *
     * @param key  加密key 默认：h%OG8Y3WgA5AN7&6Ke7I#C1XvneW0N8a
     * @param type 加密类型：默认JWT
     * @param iss  token发行商: 默认yexuejc.com
     * @return
     */
    public static JwtUtil config(String key, String type, String iss) {
        JwtUtil jwtUtil = instace();
        jwtUtil.JWT_SIGNATURE_KEY = key;
        jwtUtil.JWT_HEADER_TYP = type;
        jwtUtil.JWT_CLAIMS_ISS = iss;
        return jwtUtil;
    }

    public static class Instace {
        private static JwtUtil jwtUtil = new JwtUtil();
    }

    /**
     * 加密用KEY
     */
    private String JWT_SIGNATURE_KEY = "h%OG8Y3WgA5AN7&6Ke7I#C1XvneW0N8a";
    /**
     * token类型
     */
    private String JWT_HEADER_TYP = "JWT";
    /**
     * token发行商
     */
    private String JWT_CLAIMS_ISS = "yexuejc.com";

    /**
     * 加密内容生成token
     *
     * @param subjectObj
     * @return
     */
    public String compact(Object subjectObj) {
        String subject = null;
        if (subjectObj instanceof String) {
            subject = (String) subjectObj;
        } else {
            subject = JsonUtil.obj2Json(subjectObj);
        }
        Date now = new Date();
        String token = Jwts.builder()
                // 设置token的唯一标识ID（claims.jti）
                .setId(StrUtil.genUUID())
                // 设置token类型（header.typ）
                .setHeaderParam("typ", JWT_HEADER_TYP)
                // 设置token发行时间为当前时间（claims.iat）
                .setIssuedAt(now)
                // 设置token发行商/发行者（claims.iss）
                .setIssuer(JWT_CLAIMS_ISS)
                // 设置token用户定义主体（claims.sub）
                .setSubject(subject)
                // 设置签名算法和KEY（signature）
                .signWith(SignatureAlgorithm.HS512, JWT_SIGNATURE_KEY)
                // 生成token
                .compact();
        return token;
    }

    /**
     * 解密token为一个Map
     *
     * @param token
     * @return
     */
    public Map<?, ?> parse(String token) {
        return parse(token, Map.class);
    }

    /**
     * 解密token为一个指定对象
     *
     * @param token
     * @param cls
     * @return
     */
    public <T> T parse(String token, Class<T> cls) {
        String subject = null;
        try {
            subject = Jwts.parser().setSigningKey(JWT_SIGNATURE_KEY).parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (subject == null) {
            return null;
        }
        return JsonUtil.json2Obj(subject, cls);
    }

}
