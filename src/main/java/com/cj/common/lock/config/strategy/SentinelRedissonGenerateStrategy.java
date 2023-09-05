package com.cj.common.lock.config.strategy;

import com.cj.common.lock.constant.RedisConnectionType;
import com.cj.common.lock.constant.RedisConstant;
import com.cj.common.lock.config.RedissonProperties;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author ziyu
 * @version 1.0
 * @Description
 * @date 2023/9/4 16:34
 */
@Slf4j
public class SentinelRedissonGenerateStrategy implements RedissonGenerateStrategy{
    @Override
    public Redisson createRedisson(RedissonProperties redissonProperties) {
        String password = redissonProperties.getPassword();
        String[] addressInfo = redissonProperties.getAddress().split(",");
        int database = redissonProperties.getDatabase();
        Config config = new Config();
        Redisson redisson;
        try{
            String aliasName = addressInfo[0];
            config.useSentinelServers().setPassword(password);
            config.useSentinelServers().setDatabase(database);
            config.useSentinelServers().setMasterName(aliasName);
            for (int i = 1; i < addressInfo.length; i++) {
                config.useSentinelServers().addSentinelAddress(RedisConstant.REDIS_CONNECTION_PREFIX.getPrefix()+addressInfo[i]);
            }
            redisson = (Redisson)Redisson.create(config);
        }catch (Exception e){
            log.error("sentinel 模式初始化失败:{}",e);
            throw new RuntimeException(e.getMessage());
        }

        return redisson;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        RedissonGenerateFactory.register(RedisConnectionType.SENTINEL.getType(), new SentinelRedissonGenerateStrategy());
    }
}
