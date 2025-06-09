## Use Java 21 as base image
#FROM openjdk:21-jdk-slim
#
## Set working directory inside container
#WORKDIR /app
#
## Copy the built jar file to container
#COPY target/patientmanagementapi-0.0.1-SNAPSHOT.jar app.jar
#
## Expose the port Spring Boot runs on
#EXPOSE 8080
#
## Run the application
#ENTRYPOINT ["java", "-jar", "app.jar"]
