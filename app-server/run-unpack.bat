@echo off
"%JAVA_HOME%\bin\java.exe" -jar -Xmx768m "%~dp0\app-server.jar" unpack
pause