FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /build

COPY . .

RUN mvn clean package -pl Normalizador -am -DskipTests

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /build/Normalizador/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]