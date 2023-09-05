# Redisson 分布式锁实现

## 一、项目描述：

本项目实现了统一的分布式锁管理，统一的redis部署配置。目前支持单机，集群，主从，哨兵。支持读锁，写锁，可重入锁，公平锁。

## 二、项目技术相关：

redisson + spring boot + 自定义注解 + AOP + 工厂模式 + 策略模式

## 三、使用

## 1.下载

git 地址：https://github.com/zhiyuS/spring-boot-starter-redisson

### 2.yml配置

> 单机配置
```yaml
redisson:
  lock:
    server:
      address: 127.0.0.1:6379
      type: standalone
      database: 1
```
> 哨兵配置

第一个为哨兵名字，之后都是节点
```yaml
redisson:
  lock:
    server:
      address: mymaster,127.0.0.1:23679,127.0.0.1:23680,127.0.0.1:23681
      type: sentinel
      database: 1
```
> 集群配置
```yaml
redisson:
  lock:
    server:
      address: 127.0.0.1:23679,127.0.0.1:23680,127.0.0.1:23681
      type: cluster
```
> 主从配置

第一个为主节点
```yaml
redisson:
  lock:
    server:
      address: 127.0.0.1:6379,127.0.0.1:23680,127.0.0.1:23681
      type: masterslave
```
### 3.maven 引入
```xml
<dependency>
    <groupId>com.cj</groupId>
    <artifactId>spring-boot-starter-redisson</artifactId>
    <version>1.0.0</version>
</dependency>
```


### 4.注解使用
1.开启@EnableTongRedissonLock
2.在方法上使用注解
```java
@DistributedLock(value = "redis-lock",expireSeconds = 10,lockType = LockType.FAIR)

```

### 5.依赖注入
```java
@Autowired
private RedissonLock redissonLock;
```


