for /f "tokens=5" %%i in ('netstat -ano ^| findstr 8989 ^| findstr LISTENING') do (
taskkill -pid  %%i -f
)
title turbine
ping -n 3 127.0.0.1
java -jar -Xms250m -Xmx250m  turbine-jycj-1.0-SNAPSHOT.jar