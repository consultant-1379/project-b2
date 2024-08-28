From openjdk:11
copy ./target/security-assessment-0.0.1-SNAPSHOT.jar security-assessment-0.0.1-SNAPSHOT.jar
COPY ./wait-for-it.sh wait-for-it.sh
ENTRYPOINT ["./wait-for-it.sh", "mysqldb:3306", "--", "java","-jar","security-assessment-0.0.1-SNAPSHOT.jar"]