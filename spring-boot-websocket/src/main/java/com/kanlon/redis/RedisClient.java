package com.kanlon.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis包装工具类
 * 对于redisTpl.opsForValue().set(key, value)进行了一次封装，不然每次都要这样保存值
 * 而封装后只需：new RedisClient().set(key,value);
 *
 * @author zhangcanlong
 */
@Component
public class RedisClient {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 保存值到缓存里面
     *
     * @param key   要保存的key
     * @param value 要保存的对象
     * @return 返回是否保存成功
     */
    public boolean setkeyValue(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.getStackTrace();
            return false;
        }
    }

    /**
     * 通过key获取缓存里对应的value
     *
     * @param key 要获取的key值
     * @return 返回的对象
     */
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
