FROM eclipse-temurin:19-jdk
LABEL authors="aariaz"
WORKDIR /app
COPY build/libs/auth-server-0.0.1-SNAPSHOT.jar /app/app.jar
COPY /src/main/resources/sh/wait-for-it.sh /usr/local/bin/wait-for-it
RUN chmod +x /usr/local/bin/wait-for-it

ENTRYPOINT ["wait-for-it", "config:7777", "--", "java", "-jar", "/app/app.jar"]