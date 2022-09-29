#
#Build stage
#
#FROM maven:3.6.0-jdk-11-slim AS build
#COPY src /microservices/comment-service/app/src
#COPY pom.xml /microservices/comment-service/app
#RUN mvn -f /microservices/comment-service/app/pom.xml clean package


#
#Package stage
#
FROM adoptopenjdk:11-jre-hotspot as builder
# Maven build JAR name
ARG JAR_FILE=target/comment-service-0.0.1-SNAPSHOT-exec.jar
# cd /microservices/comment-service/app
WORKDIR /microservices/comment-service/app
# cp target/comment-service-0.0.1-SNAPSHOT-exec.jar --> /microservices/comment-service/app/comment-service.jar
COPY ${JAR_FILE} comment-service.jar
# Run java -jar /microservices/comment-service/app/comment-service.jar
ENTRYPOINT ["java", "-jar", "comment-service.jar"]