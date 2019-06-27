1、EUREKA-SERVICE-DISCOVERY-JYCJ http://192.168.111.121:1111/

2、EUREKA-CLIENT-LOCAL http://192.168.111.121:8001

3、API-GATEWAY-ZUUL 
http://192.168.111.121:5555
http://192.168.111.121:5555/eure-cli/hello/sayHello?name=hhh
http://192.168.111.121:5555/actuator/hystrix.stream
http://192.168.111.121:5555/consumer

4、DASHBOARD-JYCJ http://192.168.111.121:2001/hystrix
5、TURBINE-JYCJ http://192.168.111.121:8989/turbine.stream

# zipkin server 

```shell
java -jar -Xms500m -Xmx500m -Dspring.profiles.active=test zipkin-server-2.12.1-exec.jar
```

新版本官方的jar形式启动，所以需要通过下载

```
https://github.com/openzipkin/zipkin/tree/master/zipkin-server

lcoalhost:9411
```

* 默认使用http
* 后期改造 使用rabitmq 或者kafka等


# eureka server 不踢出已关闭的节点问题

server 端

```
  server:
    enable-self-preservation: false
    eviction-interval-timer-in-ms: 60000
```

client 端

```
eureka:
  instance:
    lease-expiration-duration-in-seconds: 30
    lease-renewal-interval-in-seconds: 10
  client:
    healthcheck:
      enabled: true
```




