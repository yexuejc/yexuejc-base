package com.yexuejc.base.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.MapType;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Logger;


/**
 * json工具类，基于jackson
 *
 * @author maxf
 * @ClassName JsonUtil
 * @Description
 * @date 2018/9/3 15:28
 */
public class JsonUtil {
    private static Logger log = Logger.getLogger(JsonUtil.class.getName());

    private JsonUtil() {
    }

    /**
     * 作为单例全局使用
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        JsonUtil.initDefaultObjectMapper(JsonUtil.objectMapper);
    }

    /**
     * 初始化ObjectMapper为默认属性
     *
     * @param objectMapper
     */
    private static void initDefaultObjectMapper(ObjectMapper objectMapper) {
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setDateFormat(DateUtil.DATE_TIME_FORMAT);
        //驼峰下划线互转
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    /**
     * 每调用一次生成一个全新的ObjectMapper供特殊场景使用，与通用ObjectMapper没有关系
     *
     * @return
     */
    public static ObjectMapper genObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonUtil.initDefaultObjectMapper(objectMapper);
        return objectMapper;
    }

    /**
     * 将json转换为某个类
     *
     * @param json InputStream类型json数据
     * @param cls  转换类class
     * @return 对象实例
     */
    public static <T> T json2Obj(InputStream json, Class<T> cls) {
        T pojo = null;

        try {
            pojo = objectMapper.readValue(json, cls);
        } catch (JsonParseException e) {
            log.warning("json to Object JsonParseException.\n" + e.getMessage());
        } catch (JsonMappingException e) {
            log.warning("json to Object JsonMappingException.\n" + e.getMessage());
        } catch (IOException e) {
            log.warning("json to Object IOException.\n" + e.getMessage());
        }

        return pojo;
    }

    /**
     * 将json转换为某个类
     *
     * @param json String类型json数据
     * @param cls  转换类class
     * @return 对象实例
     */
    public static <T> T json2Obj(String json, Class<T> cls) {
        T pojo = null;

        try {
            pojo = objectMapper.readValue(json, cls);
        } catch (JsonParseException e) {
            log.warning("json to Object JsonParseException.\n" + e.getMessage());
        } catch (JsonMappingException e) {
            log.warning("json to Object JsonMappingException.\n" + e.getMessage());
        } catch (IOException e) {
            log.warning("json to Object IOException.\n" + e.getMessage());
        }

        return pojo;
    }


    /**
     * Json字符串转换为Java对象
     *
     * @param json             字符串
     * @param parametrized     容器类
     * @param parameterClasses 实际类
     * @return
     */
    public static <T> T json2Obj(String json, Class<T> parametrized, Class<?>... parameterClasses) {
        T pojo = null;
        JavaType javaType = objectMapper.getTypeFactory().constructParametrizedType(parametrized, parametrized,
                parameterClasses);
        try {
            pojo = objectMapper.readValue(json, javaType);
        } catch (JsonParseException e) {
            log.warning("json to Object JsonParseException.\n" + e.getMessage());
        } catch (JsonMappingException e) {
            log.warning("json to Object JsonMappingException.\n" + e.getMessage());
        } catch (IOException e) {
            log.warning("json to Object IOException.\n" + e.getMessage());
        }
        return pojo;
    }

    /**
     * Json字符串转换为Java-Map对象
     *
     * @param json       字符串
     * @param mapClass   Map 继承类
     * @param keyClass   Key 类
     * @param valueClass Value 类
     * @param <T>
     * @return
     */
    public static <T> T json2Obj(String json, Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
        T pojo = null;
        MapType mapType = objectMapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
        try {
            pojo = objectMapper.readValue(json, mapType);
        } catch (JsonParseException e) {
            log.warning("json to Object JsonParseException.\n" + e.getMessage());
        } catch (JsonMappingException e) {
            log.warning("json to Object JsonMappingException.\n" + e.getMessage());
        } catch (IOException e) {
            log.warning("json to Object IOException.\n" + e.getMessage());
        }
        return pojo;
    }

    /**
     * Json字符串转换为Java-Map对象
     *
     * @param json      字符串
     * @param mapClass  Map 继承类
     * @param keyType   Key 类
     * @param valueType Value 类
     * @param <T>
     * @return
     */
    public static <T> T json2Obj(String json, Class<? extends Map> mapClass, JavaType keyType, JavaType valueType) {
        T pojo = null;
        MapType mapType = objectMapper.getTypeFactory().constructMapType(mapClass, keyType, valueType);
        try {
            pojo = objectMapper.readValue(json, mapType);
        } catch (JsonParseException e) {
            log.warning("json to Object JsonParseException.\n" + e.getMessage());
        } catch (JsonMappingException e) {
            log.warning("json to Object JsonMappingException.\n" + e.getMessage());
        } catch (IOException e) {
            log.warning("json to Object IOException.\n" + e.getMessage());
        }
        return pojo;
    }

    /**
     * Json字符串转换为Java对象
     *
     * @param json             字符串
     * @param parametrized     容器类
     * @param parameterClasses 实际类
     * @return
     */
    public static <T> T json2Obj(InputStream json, Class<T> parametrized, Class<?>... parameterClasses) {
        T pojo = null;
        JavaType javaType = objectMapper.getTypeFactory().constructParametrizedType(parametrized, parametrized,
                parameterClasses);
        try {
            pojo = objectMapper.readValue(json, javaType);
        } catch (JsonParseException e) {
            log.warning("json to Object JsonParseException.\n" + e.getMessage());
        } catch (JsonMappingException e) {
            log.warning("json to Object JsonMappingException.\n" + e.getMessage());
        } catch (IOException e) {
            log.warning("json to Object IOException.\n" + e.getMessage());
        }
        return pojo;
    }

    /**
     * 将任何对象转换为json
     *
     * @param pojo 要转换的对象
     * @return 返回json
     */
    public static String obj2Json(Object pojo) {
        String json = null;
        try {
            json = objectMapper.writeValueAsString(pojo);
        } catch (JsonProcessingException e) {
            log.warning("json to Object JsonProcessingException.\n" + e.getMessage());
        }
        return json;
    }
}