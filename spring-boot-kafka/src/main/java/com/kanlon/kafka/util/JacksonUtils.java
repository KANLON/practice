package com.kanlon.kafka.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * jackson的工具类
 *
 * @author zhangcanlong
 * @since 2020-02-05
 **/
@Slf4j
public class JacksonUtils {

    /**
     * 普通的 公共 ObjectMapper（如果反序列化的字符串中不存在的对应对象的属性，不会报错）
     **/
    private final static ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 序列化的时候忽略null值的公共 ObjectMapper（如果反序列化的字符串中不存在的对应对象的属性，不会报错）
     * <p>
     * 例如：对象为：{name:"张三",value:null},则序列化后为：{"name":"张三"}
     **/
    private final static ObjectMapper MAPPER_NOT_NULL = new ObjectMapper();

    /**
     * 驼峰转下划线 字符串，和下划线转驼峰对象 的ObjectMapper
     **/
    private final static ObjectMapper SNAKE_MAPPER = new ObjectMapper();

    /**
     * 对象中所有属性为null，另一个代表的json字符串
     */
    public final static String NULL_OBJ_STR = "{}";

    /**
     * 空字符串
     */
    public final static String EMPTY_STR = "";

    private JacksonUtils() {

    }

    static {
        // 默认设置，如果json字符串中不包含要转的类的属性的时候，不会报错
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        MAPPER.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);

        MAPPER_NOT_NULL.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MAPPER_NOT_NULL.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER_NOT_NULL.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        MAPPER_NOT_NULL.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        MAPPER_NOT_NULL.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        SNAKE_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        SNAKE_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SNAKE_MAPPER.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        SNAKE_MAPPER.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        SNAKE_MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    /**
     * 获取公共唯一的ObjectMapper 对象，（如果反序列化的字符串中不存在的对应对象的属性，不会报错）
     * 注意：禁止设置该对象的配置，如果需要另外单独的其他配置，请自己另外新建一个
     *
     * @return com.fasterxml.jackson.databind.ObjectMapper
     **/
    public static ObjectMapper getInstance() {
        return MAPPER;
    }

    /**
     * 序列化的时候忽略null值的ObjectMapper 对象，（如果反序列化的字符串中不存在的对应对象的属性，不会报错）
     * 注意：禁止设置该对象的配置，如果需要另外单独的其他配置，请自己另外新建一个
     *
     * @return com.fasterxml.jackson.databind.ObjectMapper
     **/
    public static ObjectMapper getNotNullInstance() {
        return MAPPER_NOT_NULL;
    }

    /**
     * 字符串key为下划线形式的，公共ObjectMapper对象（如果反序列化的字符串中不存在的对应对象的属性，不会报错）
     * 注意：禁止设置该对象的配置，如果需要另外单独的其他配置，请自己另外新建一个
     *
     * @return com.fasterxml.jackson.databind.ObjectMapper
     **/
    public static ObjectMapper getSnakeInstance() {
        return SNAKE_MAPPER;
    }

    /**
     * javaBean、列表数组、map 转换为json字符串
     *
     * @param obj      要转换的对象
     * @param throwsEx 转化失败的时候是否抛出异常
     * @return 转换后的json字符串，如果对象为null，则返回 “”
     */
    public static String obj2json(Object obj, boolean throwsEx) {
        String jsonStr = EMPTY_STR;
        if (obj == null) {
            return jsonStr;
        }
        try {
            jsonStr = MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            if (throwsEx) {
                throw new RuntimeException("对象转json错误！要转化的对象为：" + obj, e);
            } else {
                log.warn("对象转json错误！要转化的对象为：[{}]", obj, e);
            }
        }
        return jsonStr;
    }

    /**
     * javaBean、列表数组转换为json字符串,忽略空值
     *
     * @param obj      要转换的对象
     * @param throwsEx 转化失败的时候是否抛出异常
     * @return 转换后的字符串，如果对象为null，则返回 “”
     */
    public static String obj2jsonIgnoreNull(Object obj, boolean throwsEx) {
        String jsonStr = EMPTY_STR;
        if (obj == null) {
            return jsonStr;
        }
        try {
            jsonStr = MAPPER_NOT_NULL.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            if (throwsEx) {
                throw new RuntimeException("对象转json错误！要转化的对象为：" + obj, e);
            } else {
                log.warn("对象转json错误！要转化的对象为：[{}]", obj, e);
            }
        }
        return jsonStr;
    }

    /**
     * 将对象的大写转换为下划线加小写json，例如：userName-->user_name
     * <p>
     * 注意： 这里对map的key并不生效
     *
     * @param obj      要转化的对象
     * @param throwsEx 转化失败的时候是否抛出异常
     * @return 转化后的字符串，如果对象为null，则返回 “”
     */
    public static String obj2snakeJson(Object obj, boolean throwsEx) {
        String jsonStr = EMPTY_STR;
        if (obj == null) {
            return jsonStr;
        }
        try {
            jsonStr = SNAKE_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            if (throwsEx) {
                throw new RuntimeException("对象转json错误！要转化的对象为：" + obj, e);
            } else {
                log.warn("对象转json错误！要转化的对象为：[{}]", obj, e);
            }
        }
        return jsonStr;
    }

    /**
     * json 转JavaBean
     *
     * @param json  要转的json字符串
     * @param clazz 要转成的javaBean对象
     * @return 返回转后的javaBean对象
     */
    public static <T> T json2object(String json, Class<T> clazz, boolean throwsEx) {
        T t = null;
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            t = MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            if (throwsEx) {
                throw new RuntimeException("json转对象错误！要转化的字符串为：" + json, e);
            } else {
                log.warn("反序列化对象错误！要反序列化的对象为：[{}]", json, e);
            }
        }
        return t;
    }

    /**
     * 将下划线转换为驼峰的形式，例如：user_name-->userName，将字符串转为对象
     *
     * @param json     要转化的字符串
     * @param clazz    转化后的类
     * @param throwsEx 是否抛异常，如果是否转对象错误的时候返回null
     * @return 返回转化后的类
     */
    public static <T> T json2snakeObject(String json, Class<T> clazz, boolean throwsEx) {
        T t = null;
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            t = SNAKE_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            if (throwsEx) {
                throw new RuntimeException("json转对象错误！要转化的字符串为：" + json, e);
            } else {
                log.warn("反序列化对象错误！要反序列化的对象为：[{}]", json, e);
            }
        }
        return t;
    }

    /**
     * json字符串转换为map，map的value包含指定对象
     *
     * @param json 要转的json字符串
     * @return 返回转换后的map
     */
    public static <T> Map<String, T> json2map(String json, Class<T> clazz, boolean throwsEx) {
        Map<String, T> map = new LinkedHashMap<>(0);
        if (StringUtils.isEmpty(json)) {
            return map;
        }
        try {
            MapType javaType = MAPPER.getTypeFactory().constructMapType(Map.class, String.class, clazz);
            map = MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            if (throwsEx) {
                throw new RuntimeException("json转对象错误！要转化的字符串为：" + json, e);
            } else {
                log.warn("反序列化对象错误！要反序列化的对象为：[{}]", json, e);
            }
        }
        return map;
    }

    /**
     * json字符串转换为map，map的value包含指定对象,key转成下划线的形式
     * <p>
     * map中的key是不支持自动转
     *
     * @param json 要转的json字符串
     * @return 返回转换后的map
     */
    public static <T> Map<String, T> json2snakeMap(String json, Class<T> clazz, boolean throwsEx) {
        Map<String, T> map = new LinkedHashMap<>(0);
        if (StringUtils.isEmpty(json)) {
            return map;
        }
        try {
            MapType javaType = MAPPER.getTypeFactory().constructMapType(Map.class, String.class, clazz);
            map = SNAKE_MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            if (throwsEx) {
                throw new RuntimeException("json转对象错误！要转化的字符串为：" + json, e);
            } else {
                log.warn("反序列化对象错误！要反序列化的对象为：[{}]", json, e);
            }
        }
        return map;
    }

    /**
     * 深度转换json成map(还没单元测试过)
     *
     * @param jsonString 要转换的json字符串
     * @return 转换后的map
     */
    public static Map<String, Object> json2mapDeeply(String jsonString) throws IOException {
        return json2mapRecursion(jsonString);
    }

    /**
     * 字符串json数组转为list对象
     *
     * @param jsonArrayStr 要转换的json字符串
     */
    public static <T> List<T> json2list(String jsonArrayStr, Class<T> clazz, boolean throwsEx) {
        List<T> list = new ArrayList<>(0);
        if (StringUtils.isEmpty(jsonArrayStr)) {
            return list;
        }
        try {
            CollectionType javaType = MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);
            list = MAPPER.readValue(jsonArrayStr, javaType);
        } catch (IOException e) {
            if (throwsEx) {
                throw new RuntimeException("json转对象错误！要转化的字符串为：" + jsonArrayStr, e);
            } else {
                log.warn("反序列化对象错误！要反序列化的对象为：[{}]", jsonArrayStr, e);
            }
        }
        return list;
    }

    /**
     * 字符串json数组转为list对象,key为下划线形式
     *
     * @param jsonArrayStr 要转换的json字符串
     */
    public static <T> List<T> json2snakeList(String jsonArrayStr, Class<T> clazz, boolean throwsEx) {
        List<T> list = new ArrayList<>(0);
        if (StringUtils.isEmpty(jsonArrayStr)) {
            return list;
        }
        try {
            CollectionType javaType = MAPPER.getTypeFactory().constructCollectionType(List.class, clazz);
            list = SNAKE_MAPPER.readValue(jsonArrayStr, javaType);
        } catch (IOException e) {
            if (throwsEx) {
                throw new RuntimeException("json转对象错误！要转化的字符串为：" + jsonArrayStr, e);
            } else {
                log.warn("反序列化对象错误！要反序列化的对象为：[{}]", jsonArrayStr, e);
            }
        }
        return list;
    }

    /**
     * map  转JavaBean
     *
     * @param clazz       要转成javaBean的类
     * @param map         要转化的map
     * @param isSnakeCase map的key是否是下划线形式
     * @return 返回转化后的对象
     */
    public static <T> T map2obj(Map<String, Object> map, Class<T> clazz, boolean isSnakeCase) {
        if (map == null) {
            return null;
        }
        if (isSnakeCase) {
            return SNAKE_MAPPER.convertValue(map, clazz);
        } else {
            return MAPPER.convertValue(map, clazz);
        }
    }


    /**
     * JavaBean 转 map
     *
     * @param t           要转成javaBean的实体类
     * @param isSnakeCase map的key是否是下划线形式
     * @param throwsEx    转化异常的时候是否跑出错误
     * @return 返回转化后的对象
     */
    public static <T> Map<String, Object> obj2map(T t, boolean isSnakeCase, boolean throwsEx) {
        if (t == null) {
            return new LinkedHashMap<>(0);
        }
        String tempJson;
        if (isSnakeCase) {
            tempJson = obj2snakeJson(t, throwsEx);
        } else {
            tempJson = obj2json(t, throwsEx);
        }
        return json2map(tempJson, Object.class, throwsEx);
    }


    /**
     * 把json解析成list，如果list内部的元素存在jsonString，继续解析
     *
     * @param json 要解析的json字符串
     * @return 转换的list集合
     */
    private static List<Object> json2listRecursion(String json) throws IOException {
        if (json == null) {
            return null;
        }
        List<Object> list = MAPPER.readValue(json, new TypeReference<List<Object>>() {});
        for (Object obj : list) {
            if (obj == null) {
                continue;
            }
            if (obj instanceof String) {
                String str = (String) obj;
                if (str.startsWith("[")) {
                    obj = json2listRecursion(str);
                } else if (obj.toString().startsWith("{")) {
                    obj = json2mapRecursion(str);
                }
            }
        }
        return list;
    }

    /**
     * 把json解析成map，如果map内部的value存在jsonString，继续解析
     *
     * @param json 要转的json字符串
     * @return 转换后的map
     */
    private static Map<String, Object> json2mapRecursion(String json) throws IOException {
        if (json == null) {
            return new LinkedHashMap<>(0);
        }
        Map<String, Object> map = MAPPER.readValue(json, new TypeReference<Map<String, Object>>() {});
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object obj = entry.getValue();
            if (obj == null) {
                continue;
            }
            if (obj instanceof String) {
                String str = ((String) obj);
                if (str.startsWith("[")) {
                    List<?> list = json2listRecursion(str);
                    map.put(entry.getKey(), list);
                } else if (str.startsWith("{")) {
                    Map<String, Object> mapRecursion = json2mapRecursion(str);
                    map.put(entry.getKey(), mapRecursion);
                }
            }
        }
        return map;
    }

}
