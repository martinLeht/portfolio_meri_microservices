#
#Build stage
#
#FROM maven:3.6.0-jdk-11-slim AS build
#COPY src /microservices/api-gateway/app/src
#COPY pom.xml /microservices/api-gateway/app
#RUN mvn -f /microservices/api-gateway/app/pom.xml clean package


#
#Package stage
#
FROM adoptopenjdk:11-jre-hotspot as builder
# Maven build JAR name
ARG JAR_FILE=target/api-gateway-0.0.1-SNAPSHOT.jar
# cd /microservices/api-gateway/app
WORKDIR /microservices/api-gateway/app
# cp target/api-gateway-0.0.1-SNAPSHOT.jar --> /microservices/api-gateway/app/api-gateway.jar
COPY ${JAR_FILE} api-gateway.jar
# Run java -jar /microservices/api-gateway/app/api-gateway.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "api-gateway.jar"]