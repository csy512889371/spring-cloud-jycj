for /f "tokens=5" %%i in ('netstat -ano ^| findstr 8099 ^| findstr LISTENING') do (
taskkill -pid  %%i -f
)
title spring-boot-admin-8099
ping -n 3 127.0.0.1
java -jar -Xms500m -Xmx500m -Dspring.profiles.active=prod spring-boot-admin-1.0-SNAPSHOT.jar