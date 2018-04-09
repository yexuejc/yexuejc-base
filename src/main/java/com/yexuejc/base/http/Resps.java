package com.yexuejc.base.http;

import com.yexuejc.base.constant.RespsConsts;
import com.yexuejc.base.util.JsonUtil;

import java.io.Serializable;

/**
 * 网络请求统一返回类
 *
 * @PackageName: com.yexuejc.util.base
 * @Description: 网络请求统一返回类
 * @author: maxf
 * @date: 2017/12/27 16:33
 */
public class Resps<T> implements Serializable {


    /**
     * 状态
     */
    private String code;
    /**
     * 内容
     */
    private T data;
    /**
     * 消息
     */
    private String[] msg;

    public Resps() {
    }

    /**
     * 多个消息
     *
     * @param code
     * @param msg
     */
    public Resps(String code, String[] msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 单个消息
     *
     * @param code
     * @param msg
     */
    public Resps(String code, String msg) {
        this.code = code;
        this.msg = new String[]{msg};
    }

    public Resps<T> setSucc(T t) {
        setSucc(t, RespsConsts.MSG_SUCCESS_OPERATE);
        return this;
    }

    public Resps<T> setSucc(T t, String msg) {
        setSucc(t, new String[]{msg});
        return this;
    }

    public Resps<T> setSucc(T t, String[] msg) {
        this.setData(t);
        this.setCode(RespsConsts.CODE_SUCCESS);
        this.setMsg(msg);
        return this;
    }

    public Resps setErr(String code, String[] msg) {
        this.setCode(code);
        this.setMsg(msg);
        return this;
    }

    public static Resps success(String msg) {
        return new Resps(RespsConsts.CODE_SUCCESS, msg);
    }

    public static Resps success() {
        return new Resps(RespsConsts.CODE_SUCCESS, RespsConsts.MSG_SUCCESS_OPERATE);
    }

    public static Resps error(String msg) {
        return new Resps(RespsConsts.CODE_ERROR, msg);
    }

    public static Resps fail(String msg) {
        return new Resps(RespsConsts.CODE_FAIL, msg);
    }

    public static Resps error(String code, String msg) {
        return new Resps(code, msg);
    }

    public static Resps error(String code, String[] msg) {
        return new Resps(code, msg);
    }

    public static Resps fail(String code, String msg) {
        return new Resps(code, msg);
    }

    public static Resps fail(String code, String[] msg) {
        return new Resps(code, msg);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String[] getMsg() {
        return msg;
    }

    public void setMsg(String[] msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return JsonUtil.obj2Json(this);
    }
}
