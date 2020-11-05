FROM openjdk:11-jdk-slim

RUN mkdir /app
COPY build/libs/shadow-0.0.1-all.jar /app/app.jar
WORKDIR /app
CMD java -jar app.jar