# Etapa 1: Build
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

# Etapa 2: Runtime
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# Creamos la carpeta donde Dokploy montará el volumen Named
RUN mkdir -p /mnt/uploads && chmod -R 777 /mnt/uploads

COPY --from=build /app/target/FuenteDinamica-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083

# Parámetros para evitar bloqueos de red y asegurar logs
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dfile.encoding=UTF-8", "-jar", "app.jar"]
