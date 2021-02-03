package com.yexuejc.base.util;

import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 对象工具：对类的操作
 *
 * @author maxf
 * @version 1.0
 * @ClassName ObjUtil
 * @Description 对象工具：对类的操作
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
                boolean ignore = false;
                Class<?> toType = null;
                if (annotationPresent) {
                    ToUeProperty annotation = f.getAnnotation(ToUeProperty.class);
                    ignore = annotation.ignore();
                    String value = annotation.value();
                    if (StrUtil.isNotEmpty(value)) {
                        fName = value;
                    }
                    if (!annotation.type().getClass().equals(ObjUtil.class)) {
                        toType = annotation.type();
                    }
                }
                //忽略
                if (ignore) {
                    continue;
                }
                Object o = f.get(obj);
                if (null == o && !putNull) {
                    continue;
                }
                if (null == o || isPrimitive(o) || o instanceof String || o instanceof Enum) {
                    objMap.put(fName, o);
                } else if (o instanceof Date) {
                    if (toType != null) {
                        Date date = (Date) o;
                        if (toType.equals(Integer.class)) {
                            objMap.put(fName, (int) date.getTime() / 1000);
                        } else if (toType.equals(Long.class)) {
                            objMap.put(fName, date.getTime());
                        }
                    } else {
                        objMap.put(fName, o);
                    }
                } else if (o instanceof LocalDate) {
                    if (toType != null) {
                        LocalDate date = (LocalDate) o;
                        if (toType.equals(Integer.class)) {
                            objMap.put(fName, (int) DateTimeUtil.parseLong(date) / 1000);
                        } else if (toType.equals(Long.class)) {
                            objMap.put(fName, DateTimeUtil.parseLong(date));
                        } else if (toType.equals(String.class)) {
                            objMap.put(fName, DateTimeUtil.format(date));
                        }
                    } else {
                        objMap.put(fName, o);
                    }
                } else if (o instanceof LocalDateTime) {
                    if (toType != null) {
                        LocalDateTime date = (LocalDateTime) o;
                        if (toType.equals(Integer.class)) {
                            objMap.put(fName, (int) DateTimeUtil.parseLong(date) / 1000);
                        } else if (toType.equals(Long.class)) {
                            objMap.put(fName, DateTimeUtil.parseLong(date));
                        } else if (toType.equals(String.class)) {
                            objMap.put(fName, DateTimeUtil.format(date));
                        }
                    } else {
                        objMap.put(fName, o);
                    }
                } else if (o instanceof List) {
                    List list = (List) o;
                    List bodyList = new ArrayList();
                    list.forEach(it -> {
                        if (null != it) {
                            Map<String, Object> underlineMap = getUnderlineMap(it, isAnnotationAll, putNull);
                            bodyList.add(underlineMap);
                        }
                    });
                    if (bodyList.size() > 0) {
                        objMap.put(fName, bodyList);
                    }
                } else if (o instanceof Map) {
                    Map map = (Map) o;
                    if (map.size() > 0) {
                        objMap.put(fName, map);
                    }
                } else if (o instanceof Set) {
                    Set list = (Set) o;
                    Set bodyList = new HashSet();
                    list.forEach(it -> {
                        if (null != it) {
                            Map<String, Object> underlineMap = getUnderlineMap(it, isAnnotationAll, putNull);
                            bodyList.add(underlineMap);
                        }
                    });
                    if (bodyList.size() > 0) {
                        objMap.put(fName, bodyList);
                    }
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

    /**
     * 判断是否基本类型（包括String,BigDecimal,Number）
     *
     * @param obj
     * @return
     */
    public static boolean isPrimitive(Object obj) {
        if (null == obj) {
            return false;
        }
        boolean b = obj.getClass().isPrimitive()
                || obj instanceof Integer || obj instanceof Character || obj instanceof Boolean
                || obj instanceof Number || obj instanceof String || obj instanceof Double || obj instanceof Float
                || obj instanceof Short || obj instanceof Long || obj instanceof Byte;
        if (b) {
            return true;
        }
        return false;
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

}
