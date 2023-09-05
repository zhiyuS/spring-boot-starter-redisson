package com.cj.common.lock.config.strategy;

import com.cj.common.lock.config.RedissonProperties;
import org.redisson.Redisson;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author ziyu
 * @version 1.0
 * @Description
 * @date 2023/9/4 10:07
 */
public interface RedissonGenerateStrategy extends InitializingBean {

    Redisson createRedisson(RedissonProperties redissonProperties);
}
