# ========================
# Build Stage
# ========================
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# ========================
# Runtime Stage
# ========================
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy the built jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port
EXPOSE 8000

# Run application
ENTRYPOINT ["java","-jar","app.jar"]