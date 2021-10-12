# Java OpenJDK 11
FROM adoptopenjdk:11-jre-hotspot as builder

# Maven build JAR name
ARG JAR_FILE=target/eureka-naming-server-0.0.1-SNAPSHOT.jar

# cd /microservices/naming-server/app
WORKDIR /microservices/naming-server/app

# cp target/eureka-naming-server-0.0.1-SNAPSHOT.jar --> /microservices/naming-server/app/naming-server.jar
COPY ${JAR_FILE} naming-server.jar

# Run java -jar /microservices/naming-server/app/naming-server.jar
ENTRYPOINT ["java", "-jar", "naming-server.jar"]