1、EUREKA-SERVICE-DISCOVERY-JYCJ http://192.168.111.118:1111/

2、EUREKA-CLIENT-LOCAL http://192.168.111.118:8001

3、API-GATEWAY-ZUUL 
http://192.168.111.118:5555
http://localhost:5555/eure-cli/hello/sayHello?name=hhh
http://localhost:5555/actuator/hystrix.stream
http://localhost:5555/consumer

4、DASHBOARD-JYCJ http://192.168.111.118:2001/hystrix
5、TURBINE-JYCJ http://192.168.111.118:8989/turbine.stream

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

