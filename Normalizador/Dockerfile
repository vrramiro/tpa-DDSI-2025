# Etapa de compilación
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# 1. Copiamos el pom.xml PADRE (el que está en la raíz del repo)
# Esto es fundamental para que Maven resuelva la herencia
COPY pom.xml .

# 2. Copiamos el pom.xml del Normalizador en su carpeta correspondiente
COPY Normalizador/pom.xml Normalizador/

# 3. Copiamos el código fuente del Normalizador
COPY Normalizador/src Normalizador/src

# 4. Compilamos apuntando específicamente al pom del Normalizador
RUN mvn -f Normalizador/pom.xml clean package -DskipTests

# Etapa de ejecución (Runtime)
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# Ojo acá: buscamos el .jar generado dentro de la carpeta target del módulo
COPY --from=build /app/Normalizador/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]