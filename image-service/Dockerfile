# Java OpenJDK 11
FROM adoptopenjdk:11-jre-hotspot as builder

# Maven build JAR name
ARG JAR_FILE=target/image-service-0.0.1-SNAPSHOT.jar

# cd /microservices/image-service/app
WORKDIR /microservices/naming-server/app

# cp target/image-service-0.0.1-SNAPSHOT.jar --> /microservices/image-service/app/image-service.jar
COPY ${JAR_FILE} image-service.jar

# Run java -jar /microservices/image-service/app/image-service.jar
ENTRYPOINT ["java", "-jar", "image-service.jar"]