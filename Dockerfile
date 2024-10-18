# Use OpenJDK image to run the Spring Boot app
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the built jar file from target directory (update with your jar name)
COPY target/spendWise-0.0.1.jar /app/spendWise-0.0.1.jar

# Expose the port that the Spring Boot app runs on
EXPOSE 8080

# Command to run the Spring Boot app
ENTRYPOINT ["java", "-jar", "/app/spendWise-0.0.1.jar"]
