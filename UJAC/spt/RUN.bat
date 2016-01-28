@echo off
start "run" "%~dp0\jre6\bin\javaw" -jar -Xmx768m "%~dp0\scheduler_production_tasks.jar" rem home