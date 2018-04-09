package com.yexuejc.base.constant;

/**
 * 网络请求统一返回 常量
 *
 * @PackageName: com.yexuejc.util.base
 * @Description:
 * @author: maxf
 * @date: 2017/12/27 16:47
 */
public class RespsConsts {

    private RespsConsts() {
    }

    /**
     * HTTP请求Header字段，用户登录认证凭证
     */
    public static String HEADER_AUTHORIZATION = "Authorization";
    /**
     * HTTP请求Header字段，用户未登录时返回认证要求
     */
    public static String HEADER_WWW_AUTHENTICATE = "www-authenticate";
    /**
     * HTTP请求Header字段，客户端信息
     */
    public static String HEADER_X_USER_AGENT = "X-User-Agent";
    /**
     * HTTP请求Header字段，Content-Type
     */
    public static String HEADER_CONTENT_TYPE = "Content-Type";

    /**
     * www-authenticate字段可选值，jwt认证
     */
    public static String WWW_AUTHENTICATE_JWT = "jwt";

    /**
     * 成功
     */
    public static final String CODE_SUCCESS = "S";
    public static final String MSG_SUCCESS_HTTP = "请求成功";
    public static final String MSG_SUCCESS_OPERATE = "操作成功";
    /**
     * 失败
     */
    public static final String CODE_FAIL = "F";
    public static final String MSG_FAIL_HTTP = "请求失败";
    public static final String MSG_FAIL_OPERATE = "操作失败";
    /**
     * 错误
     */
    public static final String CODE_ERROR = "E";
    public static final String MSG_ERROT_HTTP = "请求错误";
    public static final String MSG_ERROT_OPERATE = "操作错误";

    /**
     * 参数校验错误
     */
    public static final String CODE_VALIDATION = "V";
    public static final String MSG_VALIDATION = "参数错误";
}
