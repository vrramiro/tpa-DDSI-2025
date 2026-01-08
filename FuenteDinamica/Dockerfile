# ETAPA 1: Compilación
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

# ETAPA 2: Ejecución
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# 1. Creamos la carpeta en una ruta que NO sea /app para evitar colisiones
RUN mkdir -p /mnt/multimedia

# 2. Le damos permisos totales (lectura/escritura/ejecución) a todo el mundo
# Esto es lo que evita que el volumen de Dokploy bloquee a Java
RUN chmod -R 777 /mnt/multimedia

COPY --from=build /app/target/FuenteDinamica-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083

# 3. Forzamos a Java a usar un generador de entropía que no se bloquee por el disco
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dfile.encoding=UTF-8", "-jar", "app.jar"]
