# ETAPA 1: Compilación
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true -Dfile.encoding=UTF-8

# ETAPA 2: Ejecución (Usamos JRE Focal para mejor compatibilidad con Dokploy)
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# Creamos la carpeta y le damos permisos totales (777)
# Esto garantiza que Java pueda escribir aunque Dokploy monte el volumen como root
RUN mkdir -p /mnt/multimedia && chmod -R 777 /mnt/multimedia

COPY --from=build /app/target/FuenteDinamica-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083

# Parámetro crítico para evitar que el log quede negro (entropía de red)
# y forzamos el encoding para que no haya líos Windows -> Linux
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dfile.encoding=UTF-8", "-jar", "app.jar"]
