package com.yexuejc.base.pojo;

import com.yexuejc.base.util.JsonUtil;

import java.io.Serializable;

/**
 * 基类VO
 * @PackageName: com.yexuejc.util.base.pojo
 * @Description:
 * @author: maxf
 * @date: 2018/1/17 13:58
 */
public class BaseVO implements Serializable {

    private static final long serialVersionUID = -1442656950873492155L;

    public static interface Add {
    }

    public static interface Del {
    }

    public static interface Mdfy {
    }

    public static interface Srch {
    }

    @Override
    public String toString() {
        return JsonUtil.obj2Json(this);
    }
}
