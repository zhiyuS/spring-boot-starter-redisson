package com.cj.common.lock.constant;

/**
 * @author ziyu
 * @version 1.0
 * @Description
 * @date 2023/9/4 15:06
 */
public enum  RedisConstant {
    REDIS_CONNECTION_PREFIX("redis://", "Redis地址配置前缀"),
    ;

    RedisConstant(String prefix, String desc) {
        this.prefix = prefix;
        this.desc = desc;
    }

    private String prefix;
    private String desc;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
