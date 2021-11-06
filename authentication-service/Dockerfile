#
#Build stage
#
#FROM maven:3.6.0-jdk-11-slim AS build
#COPY src /microservices/authentication-service/app/src
#COPY pom.xml /microservices/authentication-service/app
#RUN mvn -f /microservices/authentication-service/app/pom.xml clean package


#
#Package stage
#
FROM adoptopenjdk:11-jre-hotspot as builder
# Maven build JAR name
ARG JAR_FILE=target/authentication-service-0.0.1-SNAPSHOT.jar
# cd /microservices/authentication-service/app
WORKDIR /microservices/authentication-service/app
# cp target/authentication-service-0.0.1-SNAPSHOT.jar --> /microservices/authentication-service/app/authentication-service.jar
COPY ${JAR_FILE} authentication-service.jar
# Run java -jar /microservices/authentication-service/app/authentication-service.jar
ENTRYPOINT ["java", "-jar", "authentication-service.jar"]