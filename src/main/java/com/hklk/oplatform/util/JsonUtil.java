package com.hklk.oplatform.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

/**
 * JSON转换工具类
 * 
 */
public final class JsonUtil {

    /**
     * 将对象转换成json
     * 
     * @param obj
     * @return
     */
    public static String toJson(final Object obj) {
        return gson.toJson(obj);
    }

    /**
     * 将对象转换成json(并自定义日期格式)
     * 
     * @param obj
     * @param dateformat
     * @return
     */
    public static String toJson(final Object obj, final String dateformat) {
        if (obj == null || isBlank(dateformat)) {
            return toJson(obj);
        }
        Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Date.class, new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                SimpleDateFormat format = new SimpleDateFormat(dateformat);
                return new JsonPrimitive(format.format(src));
            }
        }).setDateFormat(dateformat).serializeNulls().create();
        return gson.toJson(obj);
    }

    /**
     * 将对象中被@Expose注解的属性转换成json
     * 
     * @param obj
     * @return
     */
    public static String toJsonWithExpose(final Object obj) {
        return gsonWithExpose.toJson(obj);
    }

    /**
     * 将对象中被@Expose注解的属性转换成json(并自定义日期格式)
     * 
     * @param obj
     * @param dateformat
     * @return
     */
    public static String toJsonWithExpose(final Object obj, final String dateformat) {
        if (obj == null || isBlank(dateformat)) {
            return toJsonWithExpose(obj);
        }
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().registerTypeHierarchyAdapter(Date.class, new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                SimpleDateFormat format = new SimpleDateFormat(dateformat);
                return new JsonPrimitive(format.format(src));
            }
        }).setDateFormat(dateformat).serializeNulls().create();
        return gson.toJson(obj);
    }

    /**
     * 不序列化对象的空值
     * 
     * @param obj
     * @return
     */
    public static String toJsonDefault(final Object obj) {
        return gsonDefault.toJson(obj);
    }

    /**
     * 将对象转换成json，忽略属性名含有str的属性
     * 
     * @param obj
     * @param str
     * @return
     */
    public static String toJsonSkipFieldByStr(final Object obj, final String str) {
        if (obj == null) {
            return toJson(obj);
        }
        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                return field.getName().indexOf(str) > -1;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).serializeNulls().create();
        return gson.toJson(obj);
    }

    /**
     * 将对象转换成json，只保留fields属性
     * 
     * @param obj
     * @param fields
     * @return
     */
    public static String toJsonKeepFields(final Object obj, final String... fields) {
        if (obj == null) {
            return toJson(obj);
        }
        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                for (String fieldName : fields) {
                    if (fieldName.equals(field.getName())) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).serializeNulls().create();
        return gson.toJson(obj);
    }

    
    /**
     * 将json格式转换成List对象
     * 
     * @param json
     * @param type
     *            如： Type type = new TypeToken<List<?>>() {}.getType();
     * @return
     */
    public static <T> List<T> jsonToList(String json, Type type) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(json, type);
        }
        return list;
    }
    
    /**
     * 将json转换成任意对象
     * 
     * @param json
     * @return
     */
    public static <T> T jsonToObject(final String json) {
        Type type = new TypeToken<T>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    /**
     * 将json转换成bean对象
     * 
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T jsonToBean(final String json, final Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
    
    /**
     * 将json转换成bean对象
     * 
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T jsonToBeanDateForLong(String json, Class<T> clazz) {
        T obj = null;

        Gson builder = new GsonBuilder().registerTypeHierarchyAdapter(Date.class, new JsonDeserializer() {

            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }

        }).setDateFormat(DateFormat.LONG).serializeNulls().create();

        obj = builder.fromJson(json, clazz);
        return obj;
    }

    /**
     * 将json转换成map对象
     * 
     * @param json
     * @return
     */
    public static Map<Object, Object> jsonToMap(final String json) {
        Map<Object, Object> map = null;
        Type type = new TypeToken<Map<Object, Object>>() {
        }.getType();
        map = gson.fromJson(json, type);
        if (map == null) {
            map = new HashMap<Object, Object>();
        }
        return map;
    }
    
    
    
    /**
     * 将json转换成map对象
     * 
     * @param json
     * @return
     */
    public static Map<String, String> jsonToMapStr(final String json) {
        Map<String, String> map = null;
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        map = gson.fromJson(json, type);
        if (map == null) {
            map = new HashMap<String, String>();
        }
        return map;
    }

    /**
     * 将json转换成List对象
     * 
     * @param json
     * @return
     */
    public static <T> List<T> jsonToList(final String json) {
        List<T> list = null;
        Type type = new TypeToken<List<T>>() {
        }.getType();
        list = gson.fromJson(json, type);
        if (list == null) {
            list = new ArrayList<T>();
        }
        return list;
    }

    /**
     * 在json字符串中，根据key值找到value
     * 
     * @param json
     * @param key
     * @return
     */
    public static Object getValue(final String json, final String key) {
        return jsonToMap(json).get(key);
    }

    /**
     * 为了不引入包，从StringUtils中复制过来
     * 
     * @param cs
     * @return
     */
    private static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * @author 曹良峰
     * @Description 反射获取class
     * @Date 19:14 2018/6/19
     * @Param [clz]
     * @Return java.lang.Class<java.lang.Object>
     **/
    public static Class<Object> getSuperClassGenricType(final Class clz) {
        Type type = clz.getGenericSuperclass();
        if(!(type instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType)type).getActualTypeArguments();
        if(!(params[0] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[0];
    }

    private static Gson gson;
    private static Gson gsonDefault;
    private static Gson gsonWithExpose;
    static {
        gson = new GsonBuilder().serializeNulls().create();
        gsonDefault = new Gson();
        // 没有@Expose注解的属性将不会被序列化
        gsonWithExpose = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().serializeNulls().create();
    }

    private JsonUtil() {
    }
}