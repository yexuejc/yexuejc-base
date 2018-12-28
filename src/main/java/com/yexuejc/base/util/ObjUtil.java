package com.yexuejc.base.util;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.sun.org.apache.xml.internal.serializer.OutputPropertyUtils;

import java.io.*;

/**
 * 对象工具：对类的操作
 *
 * @author maxf
 * @version 1.0
 * @ClassName ObjUtil
 * @Description
 * @date 2018/12/28 15:54
 */
public class ObjUtil {
    private ObjUtil() {
    }

    /**
     * <h2>深度克隆对象</h2>
     * <p>
     * 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
     * </p>
     * <i>注：克隆对象必须序列化</i>
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> T depthClone(T t) {
        T outer = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(t);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            outer = (T) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return outer;
    }

//    public static void main(String[] args) {
////        test1();
////        test2();
//
//    }
//
//    private static void test2() {
//        B t = new B();
//        t.sex = "男";
//        t.age = 18;
//        A test = new A();
//        test.name = "张三";
//        t.test = test;
//        B b = depthClone(t);
//        System.out.println(JsonUtil.obj2Json(b));
//    }
//
//    static class A implements Serializable {
//        private static final long serialVersionUID = -8462118058721865488L;
//        public String name;
//    }
//
//    static class B implements Serializable {
//        private static final long serialVersionUID = 3297717505428005316L;
//        public int age;
//        public String sex;
//        public A test;
//    }
//
//    static class C implements Serializable {
//        private static final long serialVersionUID = 3297717505428005316L;
//        public int age;
//        public String sex;
//        public A test;
//    }
//
//    private static void test1() {
//        ApiVO apiVO = new ApiVO(ApiVO.STATUS.S);
//        apiVO.setMsg("asdsadsad");
//        apiVO.setObject1("sadsadsad");
//
//        Resps<String> obj = new Resps<>();
//        obj.setSucc("安达圣斗士", "ok");
//        System.out.println(obj);
//        apiVO.setObject2(obj);
//        ApiVO apiVO1 = depthClone(apiVO);
//        System.out.println(apiVO == apiVO1);
//        System.out.println(JsonUtil.obj2Json(apiVO1));
//        System.out.println(JsonUtil.obj2Json(apiVO1.getObject1(String.class)));
//        System.out.println(JsonUtil.obj2Json(apiVO1.getObject2(Resps.class)));
//        System.out.println(apiVO1.getObject2(Resps.class) == obj);
//    }
}
