# ==========================
# Etapa 1: Build del JAR con Maven
# ==========================
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# 1. Copiar archivos de configuración (Padre e Hijo)
COPY pom.xml .
COPY FuenteDinamica/pom.xml FuenteDinamica/

# 2. Instalar el padre (CRÍTICO para que no falle el build)
RUN mvn install -N

# 3. Copiar el código fuente REAL de FuenteDinamica
COPY FuenteDinamica/src FuenteDinamica/src

# 4. Compilar SOLO FuenteDinamica explícitamente
RUN mvn clean package -DskipTests -f FuenteDinamica/pom.xml

# ==========================
# Etapa 2: Imagen final (solo el JAR)
# ==========================
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# 5. Copiar el JAR desde la ruta correcta del módulo
COPY --from=builder /app/FuenteDinamica/target/*.jar app.jar

EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]