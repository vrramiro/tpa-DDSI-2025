# ==========================
# Etapa 1: Build del JAR con Maven
# ==========================
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# 1. Copiar archivos de configuración (Padre e Hijo)
COPY pom.xml .
COPY FuenteEstatica/pom.xml FuenteEstatica/

# 2. Instalar el padre para que el hijo lo encuentre (Paso CRÍTICO que te falta)
RUN mvn install -N

# 3. Copiar el código fuente REAL de FuenteEstatica
COPY FuenteEstatica/src FuenteEstatica/src

# 4. Compilar SOLO FuenteEstatica explícitamente
RUN mvn clean package -DskipTests -f FuenteEstatica/pom.xml

# ==========================
# Etapa 2: Imagen final (solo el JAR)
# ==========================
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# 5. Copiar el JAR desde la ruta correcta del módulo (dentro de FuenteEstatica/target)
COPY --from=builder /app/FuenteEstatica/target/*.jar app.jar

EXPOSE 8083
ENTRYPOINT ["java", "-jar", "app.jar"]