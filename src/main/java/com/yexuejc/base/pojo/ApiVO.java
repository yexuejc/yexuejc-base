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

    public ApiVO(STATUS status) {
        this.status = status;
    }

    /**
     * 使用默认返回code
     *
     * @param status
     * @param msg
     */
    public ApiVO(STATUS status, String msg) {
        this.status = status;
        this.msgs = StrUtil.isNotEmpty(msg) ? new String[]{msg} : null;
    }

    /**
     * 使用默认返回code
     *
     * @param status
     * @param msg
     */
    public ApiVO(STATUS status, String[] msg) {
        this.status = status;
        this.msgs = msg;
    }

    public ApiVO(STATUS status, String code, String msg) {
        this.status = status;
        this.code = code;
        this.msgs = StrUtil.isNotEmpty(msg) ? new String[]{msg} : null;
    }

    public ApiVO(STATUS status, String code, String[] msg) {
        this.status = status;
        this.code = code;
        this.msgs = msg;
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

    public <T extends Object> ApiVO setObject1(T obj) {
        object1 = obj;
        return this;
    }

    public <T extends Object> T getObject1(Class<T> clazz) {
        return (T) object1;
    }

    public <T extends Object> ApiVO setObject2(T obj) {
        object2 = obj;
        return this;
    }

    public <T extends Object> T getObject2(Class<T> clazz) {
        return (T) object2;
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

    public ApiVO setMsg(String msg) {
        this.msgs = StrUtil.isNotEmpty(msg) ? new String[]{msg} : null;
        return this;
    }

    public boolean isFail() {
        return !isSucc();
    }

    public String getCode() {
        return code;
    }

    public ApiVO setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public String toString() {
        return JsonUtil.obj2Json(this);
    }

    public STATUS getStatus() {
        return status;
    }

    public ApiVO setStatus(STATUS status) {
        this.status = status;
        return this;
    }

    public ApiVO setMsgs(String[] msgs) {
        this.msgs = msgs;
        return this;
    }

    public String[] getMsgs() {
        return msgs;
    }

    public String getMsg() {
        return msgs == null ? "" : msgs[0];
    }
}
