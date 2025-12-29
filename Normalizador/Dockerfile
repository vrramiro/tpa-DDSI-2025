# ==========================
# Etapa 1: Build del JAR con Maven
# ==========================
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# 1. Copiar archivos de configuración (Padre e Hijo)
COPY pom.xml .
COPY Normalizador/pom.xml Normalizador/

# 2. Instalar el padre para que el hijo lo encuentre (CRÍTICO)
RUN mvn install -N

# 3. Copiar el código fuente REAL del Normalizador
COPY Normalizador/src Normalizador/src

# 4. Compilar SOLO el Normalizador explícitamente
RUN mvn clean package -DskipTests -f Normalizador/pom.xml

# ==========================
# Etapa 2: Imagen final (solo el JAR)
# ==========================
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# 5. Copiar el JAR desde la ruta correcta del módulo
COPY --from=builder /app/Normalizador/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]