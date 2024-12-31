FROM eclipse-temurin:19-jdk
LABEL authors="aariaz"
WORKDIR /app
COPY build/libs/gateway-0.0.1-SNAPSHOT.jar /app/app.jar
ENV AUTH_SERVER null
COPY /src/main/resources/sh/wait-for-it.sh /usr/local/bin/wait-for-it
RUN chmod +x /usr/local/bin/wait-for-it

ENTRYPOINT ["wait-for-it", "registry:8761", "--", "java", "-jar", "/app/app.jar"]
