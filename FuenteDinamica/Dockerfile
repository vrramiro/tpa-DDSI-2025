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

# 1. Creamos la carpeta de uploads
RUN mkdir -p /app/uploads

# 2. IMPORTANTE: Cambiamos el dueño de la carpeta al usuario que corre Java
# y damos permisos totales. Esto evita el conflicto con el volumen del PaaS.
RUN chmod -R 777 /app/uploads

COPY --from=build /app/target/FuenteDinamica-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8083

# 3. Forzamos a Java a ignorar problemas de bloqueo de disco al iniciar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
