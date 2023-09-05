package com.cj.common.lock.config.strategy;

import com.cj.common.lock.constant.RedisConnectionType;
import com.cj.common.lock.constant.RedisConstant;
import com.cj.common.lock.config.RedissonProperties;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;

/**
 * @author ziyu
 * @version 1.0
 * @Description
 * @date 2023/9/5 10:36
 */
@Slf4j
public class MasterslaveRedissonGenerateStrategy implements RedissonGenerateStrategy{
    @Override
    public Redisson createRedisson(RedissonProperties redissonProperties) {
        String password = redissonProperties.getPassword();
        String[] addressInfo = redissonProperties.getAddress().split(",");
        int database = redissonProperties.getDatabase();
        Config config = new Config();
        Redisson redisson = null;
        try {
            config.useMasterSlaveServers().setDatabase(database);
            config.useMasterSlaveServers().setMasterAddress(RedisConstant.REDIS_CONNECTION_PREFIX.getPrefix()+addressInfo[0]);
            for (int i = 1; i < addressInfo.length; i++) {
                config.useMasterSlaveServers().addSlaveAddress(RedisConstant.REDIS_CONNECTION_PREFIX.getPrefix()+addressInfo[i]);
            }
            if(password!=null){
                config.useMasterSlaveServers().setPassword(password);
            }
            redisson = (Redisson)Redisson.create(config);
        }catch (Exception e){
            log.error("masterslave 初始化失败,e:{}",e.getMessage());
            throw new RuntimeException(e);
        }
        return redisson;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        RedissonGenerateFactory.register(RedisConnectionType.MASTERSLAVE.getType(),new MasterslaveRedissonGenerateStrategy());
    }
}
