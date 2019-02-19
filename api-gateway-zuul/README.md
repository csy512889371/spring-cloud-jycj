# discovery
spring cloud中discovery service有许多种实现（eureka、consul、zookeeper等等），@EnableDiscoveryClient基于spring-cloud-commons, @EnableEurekaClient基于spring-cloud-netflix。
其实用更简单的话来说，就是如果选用的注册中心是eureka，那么就推荐@EnableEurekaClient，如果是其他的注册中心，那么推荐使用@EnableDiscoveryClient


# hystrix

1) 端点配置
```
#默认值为health,info
management:
  endpoints:
    web:
      exposure:
        include: hystrix.stream,health,info
```

注意：首先要先调用一下想监控的服务的API，否则将会显示一个空的图表

```
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>
```
zuul 本身就集成了Hystrix 可以pom 不显示引人 hystrix
Hystrix Circuit Breaker integration


2) http://localhost:5555/actuator/hystrix.stream



