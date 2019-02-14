for /f "tokens=5" %%i in ('netstat -ano ^| findstr 1111 ^| findstr LISTENING') do (
taskkill -pid  %%i -f
)
title eureka-server-jycj
ping -n 3 127.0.0.1
java -jar -Xms500m -Xmx500m -Dspring.profiles.active=prod euraka-server-jycj.jar