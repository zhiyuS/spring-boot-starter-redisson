package com.cj.common.lock;

import com.cj.common.lock.annotation.DistributedLock;
import com.cj.common.lock.constant.LockType;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author ziyu
 * @version 1.0
 * @Description
 * @date 2023/9/4 10:56
 */
@Slf4j
public class RedissonLock {
    private RedissonManager redissonManager;

    public RedissonLock(){

    }
    public RedissonLock(RedissonManager redissonManager){
        this.redissonManager = redissonManager;
    }

    public boolean lock(String lockName, long expireTime, LockType lockType){
        RLock rLock = null;
        switch (lockType){
            case FAIR:
                rLock = redissonManager.getRedisson().getFairLock(lockName);
                break;
            case WRITE:
                rLock = redissonManager.getRedisson().getReadWriteLock(lockName).writeLock();
                break;
            case READ:
                rLock = redissonManager.getRedisson().getReadWriteLock(lockName).readLock();
                break;
            case AUTO:
            case REENTRANT:
            default:
                rLock = redissonManager.getRedisson().getLock(lockName);
                break;
        }
        boolean flag = false;
        try {
             flag = rLock.tryLock(0, expireTime, TimeUnit.SECONDS);
             log.info("获取Redisson分布式锁[成功],lockName={}", lockName);
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.info("获取Redisson分布式锁[失败],lockName={},异常={}", lockName,e);
            return false;
        }
        return flag;
    }
    public void release(String lockName){
        redissonManager.getRedisson().getLock(lockName).unlock();
    }

    public RedissonManager getRedissonManager() {
        return redissonManager;
    }

    public void setRedissonManager(RedissonManager redissonManager) {
        this.redissonManager = redissonManager;
    }
}
