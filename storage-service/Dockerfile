#
#Build stage
##
#FROM maven:3.6.0-jdk-11-slim AS build
#COPY src /microservices/storage-service/app/src
#COPY pom.xml /microservices/storage-service/app
#RUN mvn -f /microservices/storage-service/app/pom.xml clean package
#

#
#Package stage
#
FROM adoptopenjdk:11-jre-hotspot as builder
# Maven build JAR name
ARG JAR_FILE=target/storage-service-0.0.1-SNAPSHOT-exec.jar
# cd /microservices/storage-service/app
WORKDIR /microservices/storage-service/app
# cp target/storage-service-0.0.1-SNAPSHOT-exec.jar --> /microservices/storage-service/app/storage-service.jar
COPY ${JAR_FILE} storage-service.jar
# Run java -jar /microservices/storage-service/app/image-service.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "storage-service.jar"]