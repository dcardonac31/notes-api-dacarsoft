# Etapa 1: Construcción del JAR
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final con JDK
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Definir las variables de entorno (puedes también pasarlas al correr el contenedor)
ENV DB_URL=jdbc:mysql://mysql-container:3306/notes?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
ENV JPA_SHOW_SQL=true
ENV SERVER_PORT=8080
ENV SPRING_PROFILES_ACTIVE=dev

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]