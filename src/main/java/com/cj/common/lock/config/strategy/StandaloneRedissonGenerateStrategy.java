package com.cj.common.lock.config.strategy;

import com.cj.common.lock.constant.RedisConstant;
import com.cj.common.lock.config.RedissonProperties;
import com.cj.common.lock.constant.RedisConnectionType;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author ziyu
 * @version 1.0
 * @Description
 * @date 2023/9/4 10:41
 */
public class StandaloneRedissonGenerateStrategy implements RedissonGenerateStrategy{

    @Override
    public void afterPropertiesSet() throws Exception {
        StandaloneRedissonGenerateStrategy standaloneRedissonGenerateStrategy = new StandaloneRedissonGenerateStrategy();
        RedissonGenerateFactory.register(RedisConnectionType.STANDALONE.getType(), standaloneRedissonGenerateStrategy);
    }

    @Override
    public Redisson createRedisson(RedissonProperties redissonProperties) {
        Config config = new Config();
        config.useSingleServer().setAddress(RedisConstant.REDIS_CONNECTION_PREFIX.getPrefix()+redissonProperties.getAddress())
        .setDatabase(redissonProperties.getDatabase())
        ;
        if(redissonProperties.getPassword()!=null){
            config.useSingleServer().setPassword(redissonProperties.getPassword());
        }
        RedissonClient redissonClient = Redisson.create(config);
        return (Redisson) redissonClient;
    }

}
