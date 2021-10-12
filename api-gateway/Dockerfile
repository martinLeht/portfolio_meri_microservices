# Java OpenJDK 11
FROM adoptopenjdk:11-jre-hotspot as builder

# Maven build JAR name
ARG JAR_FILE=target/api-gateway-0.0.1-SNAPSHOT.jar

# cd /microservices/api-gateway/app
WORKDIR /microservices/api-gateway/app

# cp target/api-gateway-0.0.1-SNAPSHOT.jar --> /microservices/api-gateway/app/api-gateway.jar
COPY ${JAR_FILE} api-gateway.jar

# Run java -jar /microservices/api-gateway/app/api-gateway.jar
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]