# Use official Eclipse Temurin JDK base image
FROM eclipse-temurin:21-jdk-jammy

# Set working directory
WORKDIR /app

# Copy the jar file from target folder (make sure it's built)
COPY target/stay-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (Spring Boot default)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
