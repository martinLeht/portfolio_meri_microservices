#
#Package stage
#
FROM adoptopenjdk:11-jre-hotspot as builder
# Maven build JAR name
ARG JAR_FILE=target/discovery-service-0.0.1-SNAPSHOT-exec.jar
# cd /microservices/discovery-service/app
WORKDIR /microservices/discovery-service/app
# cp target/discovery-service-0.0.1-SNAPSHOT-exec.jar --> /microservices/discovery-service/app/discovery-service.jar
COPY ${JAR_FILE} discovery-service.jar
# Run java -jar /microservices/discovery-service/app/discovery-service.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "discovery-service.jar"]