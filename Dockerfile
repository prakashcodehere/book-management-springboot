# 1. Use Java base image
FROM eclipse-temurin:21-jdk-jammy

# 2. Set working directory inside container
WORKDIR /app

# 3. Copy jar from target to container
COPY target/book-management-0.0.1-SNAPSHOT.jar app.jar

# 4. Expose application port
EXPOSE 9090

# 5. Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
