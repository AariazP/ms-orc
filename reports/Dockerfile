FROM eclipse-temurin:19-jdk
LABEL authors="aariaz"
WORKDIR /app
COPY build/libs/reports-0.0.1-SNAPSHOT.jar /app/app.jar
ENV EUREKA_SERVER null
ENV CONFIG_SERVER null
ENV KAFAKA_SERVER null
COPY /src/main/resources/sh/wait-for-it.sh /usr/local/bin/wait-for-it
RUN chmod +x /usr/local/bin/wait-for-it

ENTRYPOINT ["/bin/sh", "-c", "wait-for-it registry:8761 -- wait-for-it config:7777 -- java -jar /app/app.jar"]