cd  faas-service-dto
mvn clean
mvn deploy  -Dmaven.test.skip=true -U

cd  ../faas-common
mvn clean
mvn deploy  -Dmaven.test.skip=true -U

cd  ../faas-domain
mvn clean
mvn deploy  -Dmaven.test.skip=true -U

cd  ../faas-service-proxy
mvn clean
mvn deploy  -Dmaven.test.skip=true -U

cd ../faas-services

cd faas-admin-service
mvn clean
mvn package capsule:build -e -U
