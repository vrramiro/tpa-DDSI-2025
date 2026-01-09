# ETAPA 1: Compilación
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests -Dmaven.test.skip=true

# ETAPA 2: Ejecución
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Creamos la carpeta y le damos permisos totales (777)
RUN mkdir -p /mnt/multimedia && chmod -R 777 /mnt/multimedia

# Copiamos el jar del microservicio (asegúrate que el nombre coincida con tu pom.xml)
COPY --from=build /app/target/FuenteDinamica-0.0.1-SNAPSHOT.jar app.jar

# Copiamos el agente que está en la raíz de tu proyecto
COPY dd-java-agent.jar dd-java-agent.jar

EXPOSE 8083

# ENTRYPOINT corregido: incluyo el agente de Datadog y habilitamos el profiling
ENTRYPOINT ["java", \
    "-javaagent:dd-java-agent.jar", \
    "-Ddd.profiling.enabled=true", \
    "-XX:FlightRecorderOptions=stackdepth=256", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-Dfile.encoding=UTF-8", \
    "-jar", "app.jar"]




