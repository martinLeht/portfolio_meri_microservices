#
#Package stage
#
FROM adoptopenjdk:11-jre-hotspot as builder
# Maven build JAR name
ARG JAR_FILE=target/eureka-naming-server-0.0.1-SNAPSHOT.jar
# cd /microservices/naming-server/app
WORKDIR /microservices/naming-server/app
# cp target/eureka-naming-server-0.0.1-SNAPSHOT.jar --> /microservices/naming-server/app/naming-server.jar
COPY ${JAR_FILE} naming-server.jar
# Run java -jar /microservices/naming-server/app/naming-server.jar
ENTRYPOINT ["java", "-jar", "naming-server.jar"]


#MULTI STAGE with MVN build (needs fixing)
#
##
##Build stage
##
#FROM maven:3.6.0-jdk-11-slim AS build
#COPY src /microservices/naming-server/app/src
#COPY pom.xml /microservices/naming-server/app
#RUN mvn -f /microservices/naming-server/app/pom.xml clean package
#
#
##
##Package stage
##
#FROM adoptopenjdk:11-jre-hotspot as builder
## Maven build JAR name
## cd /microservices/naming-server/app
#WORKDIR /microservices/naming-server/app
## cp target/eureka-naming-server-0.0.1-SNAPSHOT.jar --> /microservices/naming-server/app/naming-server.jar
#COPY --from=build /target/eureka-naming-server-0.0.1-SNAPSHOT.jar naming-server.jar
## Run java -jar /microservices/naming-server/app/naming-server.jar
#ENTRYPOINT ["java", "-jar", "naming-server.jar"]