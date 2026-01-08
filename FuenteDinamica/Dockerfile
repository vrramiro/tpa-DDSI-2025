# ETAPA 1: Compilación
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true

# ETAPA 2: Ejecución
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# Creamos la carpeta en la RAIZ y damos permisos 777
# Esto asegura que no choque con la carpeta /app del JAR
RUN mkdir -p /uploads && chmod 777 /uploads

COPY --from=build /app/target/FuenteDinamica-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083

# Seteamos la variable interna para que coincida
ENV DIRECTORIO_UPLOADS=/uploads

# IMPORTANTE: Forzamos a Java a NO usar el disco para entropía de red
# Esto evita que el log se quede negro por bloqueos de I/O
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Xmx512m", "-jar", "app.jar"]
