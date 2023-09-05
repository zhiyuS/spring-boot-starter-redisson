package com.cj.common.lock.constant;

import lombok.Data;

/**
 * @author ziyu
 * @version 1.0
 * @Description
 * @date 2023/9/4 13:26
 */
public enum RedisConnectionType {
    STANDALONE("standalone", "单节点部署方式"),
    SENTINEL("sentinel", "哨兵部署方式"),
    CLUSTER("cluster", "集群方式"),
    MASTERSLAVE("masterslave", "主从部署方式");
    private String type;
    private String name;

    RedisConnectionType(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
