# Etapa de compilación
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -Dmaven.test.skip=true -Dfile.encoding=UTF-8

# Etapa de ejecución (Cambiamos Alpine por JRE Focal)
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# Aseguramos la existencia de la carpeta y permisos
RUN mkdir -p /app/uploads && chmod 777 /app/uploads

COPY --from=build /app/target/FuenteDinamica-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083

# Usamos una ejecución que fuerza la salida de logs y evita bloqueos de red
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dfile.encoding=UTF-8", "-jar", "app.jar"]
