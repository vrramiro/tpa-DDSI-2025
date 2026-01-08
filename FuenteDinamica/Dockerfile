# ETAPA 1: Compilación
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

# ETAPA 2: Ejecución
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# Creamos la carpeta ANTES de montar el volumen
RUN mkdir -p /app/uploads

# IMPORTANTE: Damos permisos de lectura/escritura/ejecución a TODOS los usuarios. 
# Esto evita que Docker bloquee a Java cuando se monte el volumen.
RUN chmod 777 /app/uploads

COPY --from=build /app/target/FuenteDinamica-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083

# Usamos CMD con el parámetro de seguridad para agilizar el arranque
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
