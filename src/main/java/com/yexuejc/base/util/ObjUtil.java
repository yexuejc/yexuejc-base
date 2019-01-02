package com.yexuejc.base.util;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

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
     * <p>把对象按照{@link ToUeProperty}注解转换</p>
     * <i>字段值为空不输出</i>
     *
     * @param obj 要转换的对象
     * @return 转换后的值以Map返回
     */
    public static Map<String, Object> getToUeMap(Object obj) {
        return getUnderlineMap(obj, true, false);
    }

    /**
     * <p>把对象按照注解转换</p>
     * <i>字段值为空不输出</i>
     *
     * @param obj 要转换的对象
     * @return 转换后的值toJson以String返回
     */
    public static String getToUeStr(Object obj) {
        return JsonUtil.obj2Json(getUnderlineMap(obj, true, false));
    }

    /**
     * <p>把对象驼峰字段转换成下划线输出，支持继承和字段类型为对象</p>
     * <i>字段值为空不输出</i>
     *
     * @param obj 要转换的对象
     * @return 转换后的值以Map返回
     */
    public static Map<String, Object> getUnderlineMap(Object obj) {
        return getUnderlineMap(obj, true, false);
    }

    /**
     * <p>把对象驼峰字段转换成下划线输出，支持继承和字段类型为对象</p>
     * <i>字段值为空不输出</i>
     *
     * @param obj 要转换的对象
     * @return 转换后的值toJson以String返回
     */
    public static String getUnderlineStr(Object obj) {
        return JsonUtil.obj2Json(getUnderlineMap(obj, true, false));
    }

    /**
     * 把对象字段按{@link ToUeProperty}注解规则转换输出，支持继承和字段类型为对象
     *
     * @param obj             要转换的对象
     * @param isAnnotationAll 是否全部依赖注解转换。全部依赖注解转换：true 只有字段上有注解的才转换，没有注解的默认不转换；false 有注解的依照注解转换，没有的全传下划线
     * @param putNull         是否映射null
     * @return 转换后的值toJson以String返回
     */
    public static String getUnderlineStr(Object obj, boolean isAnnotationAll, boolean putNull) {
        return JsonUtil.obj2Json(getUnderlineMap(obj, isAnnotationAll, putNull));
    }

    /**
     * 把对象字段按{@link ToUeProperty}注解规则转换成输出，支持继承和字段类型为对象
     * <p>主要功能：解决输出时驼峰-下划线的转换</p>
     *
     * @param obj             要转换的对象
     * @param isAnnotationAll 是否全部依赖注解转换。全部依赖注解转换：true 只有字段上有注解的才转换，没有注解的默认不转换；false 有注解的依照注解转换，没有的全传下划线
     * @param putNull         是否映射null
     * @return
     */
    public static Map<String, Object> getUnderlineMap(Object obj, boolean isAnnotationAll, boolean putNull) {
        Class<?> bindClass = obj.getClass();
        Map<String, Object> objMap = new HashMap<>(0);
        /*
         * 得到类中的所有属性集合
         */
        try {
            List<Field> fieldList = new ArrayList<>();
            //当父类为null的时候说明到达了最上层的父类(Object类).
            while (bindClass != null) {
                fieldList.addAll(Arrays.asList(bindClass.getDeclaredFields()));
                //得到父类,然后赋给自己
                bindClass = bindClass.getSuperclass();
            }
            for (Field f : fieldList) {
                //排除序列化
                if ("serialVersionUID".equals(f.getName())) {
                    continue;
                }
                //设置些属性是可以访问的
                f.setAccessible(true);
                String fName = f.getName();
                if (!isAnnotationAll) {
                    fName = StrUtil.camelToUnderline(f.getName());
                }
                boolean annotationPresent = f.isAnnotationPresent(ToUeProperty.class);
                if (annotationPresent) {
                    ToUeProperty annotation = f.getAnnotation(ToUeProperty.class);
                    String value = annotation.value();
                    if (StrUtil.isNotEmpty(value)) {
                        fName = value;
                    }
                }
                Object o = f.get(obj);
                if (null == o || o.getClass().isPrimitive() || o instanceof String || o instanceof Enum) {
                    if (null == o && !putNull) {
                        continue;
                    }
                    objMap.put(fName, f.get(obj));
                } else {
                    Map<String, Object> underlineMap = getUnderlineMap(o, isAnnotationAll, putNull);
                    objMap.put(fName, underlineMap);
                }
            }
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
        return objMap;
    }


    public static void main(String[] args) {
        B a = new B();
        a.nameFirst = "张三";
        a.ageInt = "5165458";
        a.testAss = "asdasdsad";
        a.setaM1("method1");
        a.setbM1("b1Mthod1");
        a.protectedStr = "protectedStr";
        a.c = new C();
        a.c.ageInt = "test";
        Map<String, Object> underlineMap = getUnderlineMap(a, false, false);
        System.out.println(JsonUtil.obj2Json(underlineMap));
    }

    static class A implements Serializable {
        private static final long serialVersionUID = -8462118058721865488L;
        public String nameFirst;
        public String ageInt;
        private String aM1;
        @ToUeProperty("p_str")
        protected String protectedStr;

        public String getaM1() {
            return aM1;
        }

        public A setaM1(String aM1) {
            this.aM1 = aM1;
            return this;
        }
    }

    static class B extends A {
        private static final long serialVersionUID = -8462118058721865488L;
        public String testAss;
        private String bM1;
        private C c;

        public String getbM1() {
            return bM1;
        }

        public B setbM1(String bM1) {
            this.bM1 = bM1;
            return this;
        }
    }

    static class C extends A {
        private static final long serialVersionUID = -8462118058721865488L;
        public String testAss;
        private String bM1;

        public String getbM1() {
            return bM1;
        }

        public C setbM1(String bM1) {
            this.bM1 = bM1;
            return this;
        }
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
