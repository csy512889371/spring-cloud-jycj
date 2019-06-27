for /f "tokens=5" %%i in ('netstat -ano ^| findstr 9411 ^| findstr LISTENING') do (
taskkill -pid  %%i -f
)
title zipkin-server
ping -n 3 127.0.0.1
java -jar -Xms250m -Xmx250m  zipkin-server-2.12.1-exec.jar