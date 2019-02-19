for /f "tokens=5" %%i in ('netstat -ano ^| findstr 2001 ^| findstr LISTENING') do (
taskkill -pid  %%i -f
)
title 2001
ping -n 3 127.0.0.1
java -jar -Xms250m -Xmx250m  dashboard-jycj-1.0-SNAPSHOT.jar