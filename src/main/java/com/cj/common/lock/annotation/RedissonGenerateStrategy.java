package com.cj.common.lock.annotation;

import com.cj.common.lock.config.strategy.MasterslaveRedissonGenerateStrategy;
import com.cj.common.lock.config.strategy.SentinelRedissonGenerateStrategy;
import com.cj.common.lock.config.strategy.StandaloneRedissonGenerateStrategy;
import com.cj.common.lock.config.strategy.ClusterRedissonGenerateStrategy;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author ziyu
 * @version 1.0
 * @Description
 * @date 2023/9/4 16:14
 */
@Inherited
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({StandaloneRedissonGenerateStrategy.class, SentinelRedissonGenerateStrategy.class, ClusterRedissonGenerateStrategy.class, MasterslaveRedissonGenerateStrategy.class})
public @interface RedissonGenerateStrategy {
}
