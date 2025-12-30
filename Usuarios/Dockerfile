# Etapa de construcción
FROM maven:3.8.5-openjdk-17-slim AS build
WORKDIR /app

# Copiamos solo el contenido del microservicio
COPY . .

# Compilamos saltando los tests para acelerar el despliegue
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM openjdk:17-jdk-slim
WORKDIR /app

# El nombre del jar suele ser artifactId-version.jar definido en el pom
COPY --from=build /app/target/usuarios-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8086
ENTRYPOINT ["java", "-jar", "app.jar"]