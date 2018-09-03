package com.yexuejc.base.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * json工具类，基于jackson
 *
 * @author maxf
 * @ClassName JsonUtil
 * @Description
 * @date 2018/9/3 15:28
 */
public class JsonUtil {
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
        } catch (JsonMappingException e) {
        } catch (IOException e) {
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
        } catch (JsonMappingException e) {
        } catch (IOException e) {
        }

        return pojo;
    }

    /**
     * Json字符串转换为Java对象
     *
     * @param json
     * @param parametrized
     * @param parameterClasses
     * @return
     */
    public static <T> T json2Obj(String json, Class<T> parametrized, Class<?>... parameterClasses) {
        T pojo = null;
        JavaType javaType = objectMapper.getTypeFactory().constructParametrizedType(parametrized, parametrized,
                parameterClasses);
        try {
            pojo = objectMapper.readValue(json, javaType);
        } catch (JsonParseException e) {
        } catch (JsonMappingException e) {
        } catch (IOException e) {
        }
        return pojo;
    }

    /**
     * Json字符串转换为Java对象
     *
     * @param json
     * @param parametrized
     * @param parameterClasses
     * @return
     */
    public static <T> T json2Obj(InputStream json, Class<T> parametrized, Class<?>... parameterClasses) {
        T pojo = null;
        JavaType javaType = objectMapper.getTypeFactory().constructParametrizedType(parametrized, parametrized,
                parameterClasses);
        try {
            pojo = objectMapper.readValue(json, javaType);
        } catch (JsonParseException e) {
        } catch (JsonMappingException e) {
        } catch (IOException e) {
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
        }
        return json;
    }
}