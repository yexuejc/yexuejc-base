package com.yexuejc.base.pojo;


import com.yexuejc.base.util.JsonUtil;
import com.yexuejc.base.util.StrUtil;

import java.io.Serializable;

/**
 * 接口交互APi
 *
 * @PackageName: com.mcworle.ecentm.api.user.web.vo
 * @Description:
 * @author: maxf
 * @date: 2018/1/17 11:36
 */
public class ApiVO implements Serializable {
    public ApiVO() {
    }

    public ApiVO(STATUS status, String code, String msg) {
        this.status = status;
        this.code = code;
        this.msgs = StrUtil.isNotEmpty(msg) ? new String[]{msg} : null;
    }

    public enum STATUS {
        /**
         * 成功
         */
        S,
        /**
         * 失败
         */
        F,
        /**
         * 错误
         */
        E
    }

    /**
     * 状态
     */
    private STATUS status = STATUS.S;
    /**
     * code
     */
    private String code;
    /**
     * 消息
     */
    private String[] msgs;


    /**
     * 值1
     */
    private Object object1;
    /**
     * 值2
     */
    private Object object2;

    public <T extends Object> void setObject1(T obj) {
        object1 = obj;
    }

    public <T extends Object> T getObject1(Class<T> clazz) {
        return (T) object1;
    }

    public <T extends Object> void setObject2(T obj) {
        object2 = obj;
    }

    public <T extends Object> T getObject2(Class<T> clazz) {
        return (T) object2;
    }

    public ApiVO setStatus(STATUS status, String code, String msg) {
        this.status = status;
        this.code = code;
        this.msgs = StrUtil.isNotEmpty(msg) ? new String[]{msg} : null;
        return this;
    }

    public ApiVO setStatus(STATUS status, String code, String[] msg) {
        this.status = status;
        this.code = code;
        this.msgs = msg;
        return this;
    }

    public boolean isSucc() {
        if (STATUS.S.name().equals(status.name())) {
            return true;
        }
        return false;
    }

    public boolean isErr() {
        if (STATUS.E.name().equals(status.name())) {
            return true;
        }
        return false;
    }

    public void setMsg(String msg) {
        this.msgs = StrUtil.isNotEmpty(msg) ? new String[]{msg} : null;
    }

    public boolean isFail() {
        return !isSucc();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return JsonUtil.obj2Json(this);
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public void setMsgs(String[] msgs) {
        this.msgs = msgs;
    }

    public String[] getMsgs() {
        return msgs;
    }

    public String getMsg() {
        return msgs == null ? "" : msgs[0];
    }
}
