package com.cj.common.lock.aspect;

import com.cj.common.lock.RedissonLock;
import com.cj.common.lock.annotation.DistributedLock;
import com.cj.common.lock.constant.LockType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author ziyu
 * @version 1.0
 * @Description
 * @date 2023/8/30 17:22
 */

@Slf4j
@Aspect
public class DistributedLockHandler {

	@Autowired
	private RedissonLock redissonLock;

	@Around("@annotation(distributedLock)")
	public Object around(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
		String lockName = distributedLock.value();
		int expireSeconds = distributedLock.expireSeconds();
		LockType lockType = distributedLock.lockType();
		boolean lock = redissonLock.lock(lockName, expireSeconds,lockType);
		if(!lock){
			throw new RuntimeException("获取锁失败");
		}
		Object proceed = joinPoint.proceed();
		redissonLock.release(lockName);
		log.info("释放锁成功lockName:{}",lockName);
		return proceed;
	}
}
