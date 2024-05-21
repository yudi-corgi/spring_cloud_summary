# Spring Cloud 常用组件示例汇总
[![](https://img.shields.io/badge/SpringCloud-Hoxton.SR4-informational?style=plastic&logo=spring)](https://github.com/1019509861/spring_cloud_summary)
[![](https://img.shields.io/badge/SpringBoot-2.3.0-yellow?style=plastic&logo=spring)](https://github.com/1019509861/spring_cloud_summary)
[![](https://img.shields.io/badge/Java-1.8-red?style=social&logo=java)](https://github.com/1019509861/spring_cloud_summary)

#### 服务注册与发现
- [x] Eureka Server/Client
- [x] Consul
- [x] Zookeeper
- [x] Nacos

#### 服务调用
- [x] Ribbon
- [x] Feign

#### 断路器与服务监控
- [x] Hystrix
- [x] Hystrix Dashboard
- [x] Turbine

#### API 网关路由
- [x] Zuul
- [x] Gateway

#### 配置中心
- [x] Config 
- [x] Bus
- [x] Nacos
- [ ] Apollo
  - 在该模块简单添加了 DeferredResult 使用示例，因为 Apollo 客户端与服务端的长连接便是基于此实现

#### 分布式事务
- [x] Seata

#### 消息路由
- [x] Stream
  - Binder / MQ 基础配置示例
  - RabbitMQ / Kafka Binder 配置示例
  - Rabbit MQ 延迟、死信、分区、多输入/多输出通道演示

#### 服务追踪
- [x] Sleuth 
- [x] Zipkin
- [ ] SkyWalking

#### 服务监控/管理/邮箱警报
- [x] Admin-Server
- [x] Actuator
- [x] spring-boot-starter-mail
