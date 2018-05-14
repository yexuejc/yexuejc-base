package com.yexuejc.base.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {
    /** 加密用KEY */
    private static String JWT_SIGNATURE_KEY = "h%OG8Y3WgA5AN7&6Ke7I#C1XvneW0N8a";
    /** token类型 */
    private static String JWT_HEADER_TYP = "JWT";
    /** token发行商 */
    private static String JWT_CLAIMS_ISS = "yexuejc.com";

    /**
     * 加密内容生成token
     * 
     * @param subjectObj
     * @return
     */
    public static String compact(Object subjectObj) {
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
    public static Map<?, ?> parse(String token) {
        return parse(token, Map.class);
    }

    /**
     * 解密token为一个指定对象
     * 
     * @param token
     * @param cls
     * @return
     */
    public static <T> T parse(String token, Class<T> cls) {
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
