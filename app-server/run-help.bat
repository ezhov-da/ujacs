@echo off
start "run" "%JAVA_HOME%\bin\java.exe" -jar -Xmx768m "%~dp0\app-server.jar" help rem home