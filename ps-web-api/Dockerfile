FROM openjdk:11-jdk-slim

RUN mkdir /app
COPY build/libs/ps-web-api-0.0.1-SNAPSHOT.jar /app/app.jar
WORKDIR /app
CMD java -jar app.jar