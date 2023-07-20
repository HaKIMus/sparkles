FROM gradle:7.2.0-jdk17 AS builder

# Set the working directory.
WORKDIR /app

# Use OpenJDK JRE for the runtime stage.
FROM openjdk:17-jdk

# Copy the jar file from the builder stage.
COPY build/libs/sparkles-*-all.jar /app/sparkles-app.jar

# Set the JVM options and run the application.
EXPOSE 8080
CMD ["java", "-jar", "/app/sparkles-app.jar"]
