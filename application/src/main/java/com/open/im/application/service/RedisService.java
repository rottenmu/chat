package com.open.im.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * <p>Title: OpenIm</p>
 * <p>Description: OpenIm</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 *
 * @author rotten
 * @version 1.0
 */

@Service
public class RedisService<T>{
    @Autowired
    private RedisTemplate<String,T> redisTemplate;

    public ValueOperations<String, T> valueOperations(){
        return redisTemplate.opsForValue();
    }

    public void put(String key, T t){
        valueOperations().set(key, t);
    }

    public void put(String key, T t, long time){
        valueOperations().set(key, t, time);
    }

    public T get(String key){
        return valueOperations().get(key);
    }

    public void remove(String key){
        redisTemplate.delete(key);
    }

    public void remove(String ...key){
        redisTemplate.delete(Arrays.asList(key));
    }
}
