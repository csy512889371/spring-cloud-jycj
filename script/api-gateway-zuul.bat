for /f "tokens=5" %%i in ('netstat -ano ^| findstr 5555 ^| findstr LISTENING') do (
taskkill -pid  %%i -f
)
title api-gateway-zuul
ping -n 3 127.0.0.1
java -jar -Xms500m -Xmx500m -Dspring.profiles.active=prod api-gateway-zuul-1.0-SNAPSHOT.jar