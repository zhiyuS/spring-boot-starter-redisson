package com.cj.common.lock.config;

import com.cj.common.lock.RedissonLock;
import com.cj.common.lock.RedissonManager;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @author ziyu
 * @version 1.0
 * @Description
 * @date 2023/9/4 11:20
 */
@Configuration
@ConditionalOnClass(Redisson.class)
@EnableConfigurationProperties(RedissonProperties.class)
@Slf4j
public class RedissonAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @Order(2)
    public RedissonLock redissonLock(RedissonManager redissonManager){
         RedissonLock redissonLock = new RedissonLock();
         redissonLock.setRedissonManager(redissonManager);
         log.info("[RedissonLock]组装完毕");
        return redissonLock;
    }
    @Bean
    @ConditionalOnMissingBean
    @Order(1)
    public RedissonManager redissonManager(RedissonProperties redissonProperties){
        RedissonManager redissonManager = new RedissonManager(redissonProperties);
        log.info("[RedissonManager]组装完毕,当前连接方式:" + redissonProperties.getType() +
                ",连接地址:" + redissonProperties.getAddress());
        return redissonManager;
    }



}
