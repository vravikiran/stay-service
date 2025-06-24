FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY target/stay-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
