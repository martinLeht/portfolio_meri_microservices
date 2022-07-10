#
#Build stage
##
#FROM maven:3.6.0-jdk-11-slim AS build
#COPY src /microservices/image-service/app/src
#COPY pom.xml /microservices/image-service/app
#RUN mvn -f /microservices/image-service/app/pom.xml clean package
#

#
#Package stage
#
FROM adoptopenjdk:11-jre-hotspot as builder
# Maven build JAR name
ARG JAR_FILE=target/image-service-0.0.1-SNAPSHOT.jar
# cd /microservices/image-service/app
WORKDIR /microservices/naming-server/app
# cp target/image-service-0.0.1-SNAPSHOT.jar --> /microservices/image-service/app/image-service.jar
COPY ${JAR_FILE} image-service.jar
# Run java -jar /microservices/image-service/app/image-service.jar
ENTRYPOINT ["java", "-jar", "image-service.jar"]