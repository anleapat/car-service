FROM openjdk:8-slim
LABEL application=car-service-1.0

RUN bash -c 'mkdir -p /app/lib'

COPY target/*.jar /app/lib/car-service-1.0-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/lib/car-service-1.0-SNAPSHOT.jar"]
