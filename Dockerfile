FROM openjdk:18-ea-jdk-slim
LABEL maintainer=henry

RUN bash -c 'mkdir -p /app/lib'

COPY target/*.jar /app/lib/car-service-1.0-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/lib/car-service-1.0-SNAPSHOT.jar"]
