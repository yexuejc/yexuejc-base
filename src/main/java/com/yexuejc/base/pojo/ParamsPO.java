package com.yexuejc.base.pojo;

import com.yexuejc.base.util.JsonUtil;

import java.io.Serializable;

/**
 * 解密前的请求参数格式
 *
 * @author: maxf
 * @date: 2018/5/12 14:52
 */
public class ParamsPO implements Serializable {
    private static final long serialVersionUID = 9171765814642105098L;

    /**
     * 参数
     */
    private String data;
    /**
     * md5
     */
    private String sign;

    @Override
    public String toString() {
        return JsonUtil.obj2Json(this);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
