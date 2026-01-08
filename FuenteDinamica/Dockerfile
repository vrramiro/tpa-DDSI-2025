# ETAPA 1: Compilación
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app

# Copiamos el archivo de configuración de dependencias
COPY pom.xml .
# Copiamos el código fuente
COPY src ./src

# Compilamos el proyecto saltando los tests para acelerar el despliegue
RUN mvn clean package -Dmaven.test.skip=true -Dfile.encoding=UTF-8

# ETAPA 2: Ejecución
FROM openjdk:17-jdk-slim
WORKDIR /app

# 1. Creamos el directorio donde se montará el volumen
# 2. Le asignamos permisos totales (777) para evitar conflictos con el usuario del PaaS
RUN mkdir -p /app/uploads && chmod -R 777 /app/uploads

# Copiamos el archivo JAR generado en la etapa anterior
COPY --from=build /app/target/FuenteDinamica-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto configurado
EXPOSE 8083

# Usamos CMD con parámetros de red optimizados para contenedores
# -Djava.security.egd ayuda a que Spring no se quede trabado al conectar a la DB
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dfile.encoding=UTF-8", "-jar", "app.jar"]
