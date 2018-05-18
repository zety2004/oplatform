package com.hklk.oplatform.comm.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

public final class RedisCache {
	
    /**
     * 批量化插入数据
     * @param params 需要入库的数据集
     * @param seconds 有效时长 缺省为2天
     * @return 操作结果
     * @author luoqy
     */
    public static boolean setBatchData(final List<Map<String, String>> params,final Long seconds){
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException{
                Long expire = null;
                if(null == seconds){
                    expire = 2*24*60*60*1000l; //两天
                }else{
                    expire = seconds;
                }
                for(Map<String, String> data : params){
                    for(Map.Entry<String, String> m : data.entrySet()){
                        connection.setEx(m.getKey().getBytes(), expire, m.getValue().getBytes());
                    }
                }
                return true;
            }
        }, false, true);
        return result;
    }
    
    /**
     * 批量化获取
     * @param keys
     * @return
     */
    public static List<Map<String, String>> getBatchData(final List<String> keys){
        List<Map<String, String>> results = redisTemplate.execute(new RedisCallback<List<Map<String,String>>>() {

            @Override
            public List<Map<String,String>> doInRedis(RedisConnection connection) throws DataAccessException {
                List<Map<String, String>> result = new ArrayList<Map<String,String>>();
                for(String key : keys){
                    Map<String, String> data = new HashMap<String, String>();
                   data.put(key, new String(connection.get(key.getBytes())));
                   result.add(data);
                }
                return result;
            }
        });
        return results;
    }
    

    /**
     * 通过key获取缓存
     * 
     * @param key
     * @return
     */
    public static String get(final String key) {
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] value = connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return result;
    }
    
    public static void hmset(final String key, final Map<String, String> map) {
        hashOperations.putAll(key, map);
    }

    public static Map<String, String> hmget(final String key) {
        return hashOperations.entries(key);
    }

    /**
     * 保存缓存
     * 
     * @param key
     * @param value
     */
    public static void set(final String key, final String value) {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(serializer.serialize(key), serializer.serialize(value));
                return null;
            }
        });
    }
    
    public static void putValue(final String key,String obj, Integer timeout) {
        if (null != obj) {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            // 设置过期时间
            operations.set(key, obj,timeout, TimeUnit.MINUTES);
        }
    } 

    /**
     * 删除缓存
     * 
     * @param key
     * @return The number of keys that were removed.
     */
    public static Long remove(final String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.del(serializer.serialize(key));
            }
        });
    }
    
    /**
     * 减法原子操作
     * 
     * @param key
     * @param number
     * @return
     */
    public static long numberSubtract(final String key, final long number) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        Object object = operations.get(key);
        if (object == null) {
            return -1;
        }
        long oldValue = Long.valueOf(object.toString());
        if (oldValue < 0) {
            return -1;
        }
        if (number - oldValue< 0) {
            return -1;
        }
        
        return -redisTemplate.opsForValue().increment(key, -number);
    }
    
    
    /**
     * 投注减法原子操作
     * 
     * @param key
     * @param number
     * @return
     */
    public static long numberBetSubtract(final String key, final long number) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        Object object = operations.get(key);
        if (object == null) {
            return -1;
        }
        long oldValue = Long.valueOf(object.toString());
        if (oldValue < 0) {
            return -1;
        }
        if (oldValue - number > 0) {
            return -2;
        }
        return -redisTemplate.opsForValue().increment(key, -number);
    }

    /**
     * 加法原子操作
     * 
     * @param key
     * @param number
     * @return
     */
    public static long numberAdd(final String key, final long number) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        Object object = operations.get(key);
        if (object == null) {
            return -1;
        }
        if (number < 0) {
            return -1;
        }
        return redisTemplate.opsForValue().increment(key, number);
    }
    
    /**
     * 通过key获得keys
     * 
     * @param key
     * @return
     */
    public static Set<String> keys(String key) {
    	Set<String> set = new HashSet<String>();
        try {
               set= redisTemplate.keys(key);
        } catch (Exception e) {
                e.printStackTrace();
        }
        return set;
    }
    
    public static void mset(Map<String, String> map) {
        valueOperations.multiSet(map);
    }

    public static List<String> mget(Set<String> set) {
        return valueOperations.multiGet(set);
    }
    
    public static void expire(final String key, final long timeout, TimeUnit timeUnit) {
        redisTemplate.expire(key, timeout, timeUnit);
    }
    
    public static void initTemplate(StringRedisTemplate redisTemplate) {
        RedisCache.redisTemplate = redisTemplate;
        serializer = RedisCache.redisTemplate.getStringSerializer();
        hashOperations = RedisCache.redisTemplate.opsForHash();
        valueOperations = RedisCache.redisTemplate.opsForValue();
    }
    

    private RedisCache() {
    }

    private static StringRedisTemplate redisTemplate;
    private static RedisSerializer<String> serializer;
    private static HashOperations<String, String, String> hashOperations;
    private static ValueOperations<String, String> valueOperations;
}
