package com.cj.common.lock.annotation;

import com.cj.common.lock.aspect.DistributedLockHandler;
import com.cj.common.lock.config.RedissonAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author ziyu
 * @version 1.0
 * @Description
 * @date 2023/9/4 11:28
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@RedissonGenerateStrategy
@Import({RedissonAutoConfiguration.class, DistributedLockHandler.class})
public @interface EnableTongRedissonLock {
}
