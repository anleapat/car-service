FROM openjdk:8-slim
LABEL application=car-service

RUN bash -c 'mkdir -p /app/lib'

COPY target/*.jar /app/lib/

ENTRYPOINT ["java", "-jar", "/app/lib/car-service-1.0-SNAPSHOT.jar"]
