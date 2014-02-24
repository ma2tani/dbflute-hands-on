%~d0
cd %~p0
cd ..

:: adding eclipse maven variable M2_REPO
:: ignored when you already set it up
cmd /c mvn -e eclipse:add-maven-repo

:: creating eclipse settings
cmd /c mvn -e eclipse:eclipse

:: refreshing this eclipse project
set pause_at_end=n
call dbflute_exampledb/manage.bat refresh

pause
