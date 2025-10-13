FROM openjdk:17-jdk-slim

WORKDIR /app

COPY  target/first-1.0.1.jar app.jar

EXPOSE 8080

ENTRYPOINT [ "java","-jar","app.jar" ]
