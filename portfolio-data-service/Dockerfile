#
#Package stage
#
FROM adoptopenjdk:11-jre-hotspot as builder
# Maven build JAR name
ARG JAR_FILE=target/portfolio-data-service-0.0.1-SNAPSHOT.jar
# cd /microservices/portfolio-data-service/app
WORKDIR /microservices/portfolio-data-service/app
# cp target/portfolio-data-service-0.0.1-SNAPSHOT.jar --> /microservices/portfolio-data-service/app/portfolio-data-service.jar
COPY ${JAR_FILE} portfolio-data-service.jar
# Run java -jar /microservices/portfolio-data-service/app/portfolio-data-service.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "portfolio-data-service.jar"]