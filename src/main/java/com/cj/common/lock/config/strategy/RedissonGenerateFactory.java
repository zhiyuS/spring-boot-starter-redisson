package com.cj.common.lock.config.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ziyu
 * @version 1.0
 * @Description
 * @date 2023/9/4 10:14
 */
@Slf4j
public class RedissonGenerateFactory {
    private static Map<String,RedissonGenerateStrategy> context = new ConcurrentHashMap<>();


    public static RedissonGenerateStrategy getGet(String type) {
        if(!context.containsKey(type)){
            log.error("不存在此类型:{}",type);
            throw new RuntimeException("不存在此类型:"+type);
        }
        return context.get(type);
    }
    public static void register(String type,RedissonGenerateStrategy redissonGenerateStrategy){
        if(type==null || redissonGenerateStrategy==null){
            log.error("type:{},redissonGenerateStrategy:{}",type,redissonGenerateStrategy);
            throw new RuntimeException("type或者redissonGenerateStrategy不能为空");
        }
        context.put(type,redissonGenerateStrategy);
    }

}
