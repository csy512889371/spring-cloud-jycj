for /f "tokens=5" %%i in ('netstat -ano ^| findstr 8001 ^| findstr LISTENING') do (
taskkill -pid  %%i -f
)
title api-gateway-zuul
ping -n 3 127.0.0.1
java -jar -Xms250m -Xmx250m  eureka-client-1.0-SNAPSHOT.jar