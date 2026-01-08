# ETAPA 1: Compilación
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app

# Copiamos el archivo de configuración de dependencias
COPY pom.xml .
# Copiamos el código fuente
COPY src ./src

# Compilamos el proyecto saltando los tests
RUN mvn clean package -Dmaven.test.skip=true -Dfile.encoding=UTF-8

# ETAPA 2: Ejecución
# Usamos eclipse-temurin que es la que te funcionó antes
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# 1. Creamos el directorio donde se montará el volumen
# 2. Le asignamos permisos totales (777) para evitar conflictos
RUN mkdir -p /app/uploads && chmod -R 777 /app/uploads

# Copiamos el archivo JAR generado en la etapa anterior
COPY --from=build /app/target/FuenteDinamica-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto configurado
EXPOSE 8083

# Ejecución robusta
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dfile.encoding=UTF-8", "-jar", "app.jar"]
