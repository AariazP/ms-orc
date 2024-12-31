FROM eclipse-temurin:19-jdk
LABEL authors="aariaz"
WORKDIR /app
COPY build/libs/config-server-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]