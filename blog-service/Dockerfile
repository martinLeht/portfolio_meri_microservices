# Java OpenJDK 11
FROM adoptopenjdk:11-jre-hotspot as builder

# Maven build JAR name
ARG JAR_FILE=target/blog-service-0.0.1-SNAPSHOT.jar

# cd /microservices/naming-server/app
WORKDIR /microservices/blog-service/app

# cp target/blog-service-0.0.1-SNAPSHOT.jar --> /microservices/blog-service/app/blog-service.jar
COPY ${JAR_FILE} blog-service.jar

# Run java -jar /microservices/blog-service/app/blog-service.jar
ENTRYPOINT ["java", "-jar", "blog-service.jar"]