FROM openjdk:8-jre-alpine
WORKDIR /app
COPY target/spboot-1.0-SNAPSHOT.jar app.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "app.jar"]
