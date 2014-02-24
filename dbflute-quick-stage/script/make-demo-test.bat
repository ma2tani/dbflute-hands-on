@echo off

setlocal
%~d0
cd %~p0

cd ..
pushd src\test\java\org\dbflute\quickstage\dbflute\thematic 
copy PrototypeOfDemoTest.prototype DemoTest.java
popd

set pause_at_end=n
call dbflute_exampledb/manage.bat refresh

pause 