1) euraka-server-jycj euraka 注册中心
2) 


# 启动脚本

```shell
java -jar -Xms500m -Xmx500m -Dspring.profiles.active=test zipkin-server-2.12.1-exec.jar
```


# zipkin server 

新版本官方的jar形式启动，所以需要通过下载

```
https://github.com/openzipkin/zipkin/tree/master/zipkin-server

lcoalhost:9411
```

* 默认使用http
* 后期改造 使用rabitmq 或者kafka等

