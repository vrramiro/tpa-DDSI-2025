# Etapa 1: Compilación
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

# Etapa 2: Ejecución
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# Creamos una carpeta en la raíz (fuera de /app) para evitar bloqueos
RUN mkdir -p /data/multimedia && chmod -R 777 /data/multimedia

COPY --from=build /app/target/FuenteDinamica-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083

# Usamos CMD directo sin ENTRYPOINT para que Dokploy gestione mejor el proceso
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
