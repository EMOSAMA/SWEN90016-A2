
set oldDir = %CD%

cd  faas-service-dto
CALL mvn clean
CALL mvn install  -Dmaven.test.skip=true
if  %errorlevel% gtr 0 (
 	:echo "error result,fire terminate" :
 	goto final
)

cd  ../faas-common
CALL mvn clean
CALL mvn install  -Dmaven.test.skip=true
if  %errorlevel% gtr 0 (
 	:echo "error result,fire terminate" :
 	goto final
)

cd  ../faas-service-proxy
CALL mvn clean
CALL mvn install  -Dmaven.test.skip=true
if  %errorlevel% gtr 0 (
	:echo "error result,fire terminate" :
	goto final
)

cd ../faas-services

cd faas-admin-service
CALL mvn clean
CALL mvn package capsule:build -e
if  %errorlevel% gtr 0 (
	:echo "error result,fire terminate" :
	goto final
)


cd /faas-admin-service
CALL mvn clean
CALL mvn package capsule:build -e
if  %errorlevel% gtr 0 (
	:echo "error result,fire terminate" :
	goto final
)

cd ../faas-portal-service
CALL mvn clean
CALL mvn package capsule:build -e
if  %errorlevel% gtr 0 (
	:echo "error result,fire terminate" :
	goto final
)

:final
	cd ..
	cd ..
	:echo %oldDir%:
