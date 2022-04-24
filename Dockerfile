FROM 192.168.1.37:5000/openjdk:8-buster
LABEL application=car-service

RUN bash -c 'mkdir -p /app/lib'
RUN bash -c 'mkdir -p /applogs/car-service'
RUN bash -c 'ln -sf /usr/share/zoneinfo/Asia/Singapore /etc/localtime'
RUN bash -c 'echo "Asia/Singapore" >/etc/timezone'

COPY target/*.jar /app/lib/

ENTRYPOINT ["java", "-jar", "/app/lib/car-service-1.0-SNAPSHOT.jar"]
