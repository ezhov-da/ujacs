@echo off
start "run" "%JAVA_HOME%\bin\javaw.exe" -jar -Xmx768m "%~dp0\app-server.jar" gui rem home