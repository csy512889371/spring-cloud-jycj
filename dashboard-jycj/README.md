# dashboard

```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
        </dependency>
```
1、http://localhost:2001/hystrix
2、在上面的输入框填入: http://想监控的服务:端口/actuator/hystrix.stream进行测试


