package com.cj.common.lock.constant;

/**
 * @author ziyu
 * @version 1.0
 * @Description
 * @date 2023/9/5 11:30
 */
public enum LockType {
    //可重入锁
    REENTRANT,
    //公平锁
    FAIR,
    //联锁
    MULTIPLE,
    //红锁
    REDLOCK,
    //读锁
    READ,
    //写锁
    WRITE,
    //自动模式,当参数只有一个.使用 REENTRANT 参数多个 REDLOCK
    AUTO
}
