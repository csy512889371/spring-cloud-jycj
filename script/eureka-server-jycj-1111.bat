for /f "tokens=5" %%i in ('netstat -ano ^| findstr 1111 ^| findstr LISTENING') do (
taskkill -pid  %%i -f
)
title eureka-server-jycj
ping -n 3 127.0.0.1
java -jar -Xms500m -Xmx500m -Dspring.profiles.active=dev eureka-server-jycj-1.0-SNAPSHOT.jar