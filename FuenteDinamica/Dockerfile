# ETAPA 1: Compilación (Sigue igual)
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

# ETAPA 2: Ejecución
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# 1. Creamos el directorio
RUN mkdir -p /app/uploads

# 2. Le damos permisos totales a la carpeta antes de que se monte el volumen
RUN chmod 777 /app/uploads

COPY --from=build /app/target/FuenteDinamica-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083

# Definimos la variable de entorno
ENV DIRECTORIO_UPLOADS=/app/uploads

# 3. Agregamos un parámetro para que Java no se bloquee si el volumen tarda
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
