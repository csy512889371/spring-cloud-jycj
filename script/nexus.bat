for /f "tokens=5" %%i in ('netstat -ano ^| findstr 8081 ^| findstr LISTENING') do (
taskkill -pid  %%i -f
)
title nexus
ping -n 3 127.0.0.1
D:\server\nexus-3.15.2-01\bin\nexus.exe /run
