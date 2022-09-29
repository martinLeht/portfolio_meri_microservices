#
#Build stage
#
#FROM maven:3.6.0-jdk-11-slim AS build
#COPY src /microservices/email-service/app/src
#COPY pom.xml /microservices/email-service/app
#RUN mvn -f /microservices/email-service/app/pom.xml clean package


#
#Package stage
#
FROM adoptopenjdk:11-jre-hotspot as builder
# Maven build JAR name
ARG JAR_FILE=target/email-service-0.0.1-SNAPSHOT-exec.jar
# cd /microservices/email-service/app
WORKDIR /microservices/email-service/app
# cp target/email-service-0.0.1-SNAPSHOT-exec.jar --> /microservices/email-service/app/email-service.jar
COPY ${JAR_FILE} email-service.jar
# Run java -jar /microservices/email-service/app/email-service.jar
ENTRYPOINT ["java", "-jar", "email-service.jar"]