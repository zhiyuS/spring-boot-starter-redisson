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
 * @date 2023/9/4 16:17
 */
@Slf4j
public class ClusterRedissonGenerateStrategy implements RedissonGenerateStrategy{
    @Override
    public Redisson createRedisson(RedissonProperties redissonProperties) {
        Config config = new Config();
        Redisson redisson = null;
        try {
            String password = redissonProperties.getPassword();
            String addressInfo = redissonProperties.getAddress();
            String[] addressList = addressInfo.split(",");
            for (String address : addressList) {
                config.useClusterServers().addNodeAddress(RedisConstant.REDIS_CONNECTION_PREFIX.getPrefix()+address);
            }
            if(password!=null){
                config.useClusterServers().setPassword(password);
            }
            redisson = (Redisson) Redisson.create(config);
            log.info("cluster redis 初始化成功");
        }catch (Exception e){
            log.error("cluster redis 初始化失败,e:{}",e);
            throw new RuntimeException("cluster redis 初始化失败,e:"+e);
        }
        return redisson;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        RedissonGenerateFactory.register(RedisConnectionType.CLUSTER.getType(),new ClusterRedissonGenerateStrategy());
    }
}
