package com.cj.common.lock.annotation;

import com.cj.common.lock.constant.LockType;

import java.lang.annotation.*;

/**
 * @author ziyu
 * @version 1.0
 * @Description
 * @date 2023/8/30 17:46
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DistributedLock {
	/**
	 * 分布式锁名称
	 *
	 * @return String
	 */
	String value() default "tong-distributed-lock-redisson";

	/**
	 * 锁超时时间,默认十秒
	 *
	 * @return int
	 */
	int expireSeconds() default 10;
	LockType lockType() default LockType.AUTO;
}
