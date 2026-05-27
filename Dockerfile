# Java 17 image
FROM eclipse-temurin:17-jdk-alpine

# Working directory
WORKDIR /app

# Project files copy karega
COPY . .

# Maven wrapper permission
RUN chmod +x mvnw

# JAR build karega
RUN ./mvnw clean package -DskipTests

# Port expose
EXPOSE 8080

# Spring Boot app run
ENTRYPOINT ["java","-jar","target/quickbill.jar"]
