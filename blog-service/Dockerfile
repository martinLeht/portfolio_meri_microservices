#
#Build stage
#
#FROM maven:3.6.0-jdk-11-slim AS build
#COPY src /microservices/blog-service/app/src
#COPY pom.xml /microservices/blog-service/app
#RUN mvn -f /microservices/blog-service/app/pom.xml clean package


#
#Package stage
#
FROM adoptopenjdk:11-jre-hotspot as builder
# Maven build JAR name
ARG JAR_FILE=target/blog-service-0.0.1-SNAPSHOT.jar
# cd /microservices/blog-service/app
WORKDIR /microservices/blog-service/app
# cp target/blog-service-0.0.1-SNAPSHOT.jar --> /microservices/blog-service/app/blog-service.jar
COPY ${JAR_FILE} blog-service.jar
# Run java -jar /microservices/blog-service/app/blog-service.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "blog-service.jar"]