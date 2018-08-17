package com.yexuejc.base.pojo;

import com.yexuejc.base.util.JsonUtil;

import java.io.Serializable;

/**
 * 基类VO
 *
 * @PackageName: com.yexuejc.util.base.pojo
 * @Description:
 * @author: maxf
 * @date: 2018/1/17 13:58
 */
public class BaseVO implements Serializable {

    private static final long serialVersionUID = -1442656950873492155L;

    public interface Add {
    }

    public interface Del {
    }

    public interface Mdfy {
    }

    public interface Srch {
    }

    @Override
    public String toString() {
        return JsonUtil.obj2Json(this);
    }
}
