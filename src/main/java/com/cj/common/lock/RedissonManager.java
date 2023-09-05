package com.cj.common.lock;

import com.cj.common.lock.config.strategy.RedissonGenerateFactory;
import com.cj.common.lock.config.strategy.RedissonGenerateStrategy;
import com.google.common.base.Preconditions;
import com.cj.common.lock.config.RedissonProperties;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ziyu
 * @version 1.0
 * @Description
 * @date 2023/8/31 17:41
 */
@Slf4j
public class RedissonManager {

    private Redisson redisson = null;
    @Autowired
    private RedissonProperties redissonProperties;

    public RedissonManager(RedissonProperties redissonProperties){
        try {
            redisson = RedissonFactory.getInstance().createRedisson(redissonProperties);
        }catch (Exception e){
            log.error("请输入正确的连接方式目前支持standalone,sentinel,cluster,masterslave");
            throw new RuntimeException("请输入正确的连接方式目前支持standalone,sentinel,cluster,masterslave");
        }
    }

    public Redisson getRedisson(){
        return redisson;
    }
    static class RedissonFactory{
        private Config config = new Config();
        public static volatile RedissonFactory redissonFactory;

        private RedissonFactory(){

        }
        public static RedissonFactory getInstance(){
            if(redissonFactory==null){
                synchronized (RedissonFactory.class){
                    if(redissonFactory==null){
                        redissonFactory = new RedissonFactory();
                    }
                }
            }
            return redissonFactory;
        }
        public Redisson createRedisson(RedissonProperties redissonProperties){
            Preconditions.checkNotNull(redissonProperties);
            Preconditions.checkNotNull(redissonProperties.getAddress(), "redisson.lock.server.address cannot be NULL!");
            Preconditions.checkNotNull(redissonProperties.getType(), "redisson.lock.server.password cannot be NULL");
            Preconditions.checkNotNull(redissonProperties.getDatabase(), "redisson.lock.server.database cannot be NULL");

            String connectionType = redissonProperties.getType();
            Redisson redisson = null;
            try{
                RedissonGenerateStrategy redissonGenerateStrategy = RedissonGenerateFactory.getGet(connectionType);
                redisson = redissonGenerateStrategy.createRedisson(redissonProperties);
            }catch (Exception e){
                throw new IllegalArgumentException("创建Redisson失败！当前连接方式:" + connectionType);
            }
            return redisson;
        }
    }

}
