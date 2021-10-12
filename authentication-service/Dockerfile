# Java OpenJDK 11
FROM adoptopenjdk:11-jre-hotspot as builder

# Maven build JAR name
ARG JAR_FILE=target/authentication-service-0.0.1-SNAPSHOT.jar

# cd /microservices/naming-server/app
WORKDIR /microservices/authentication-service/app

# cp target/authentication-service-0.0.1-SNAPSHOT.jar --> /microservices/authentication-service/app/authentication-service.jar
COPY ${JAR_FILE} authentication-service.jar

# Run java -jar /microservices/authentication-service/app/authentication-service.jar
ENTRYPOINT ["java", "-jar", "authentication-service.jar"]