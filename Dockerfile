# Compilar el proyecto entero
FROM maven:3.9.6-eclipse-temurin-21-jammy AS build
COPY . .
RUN mvn clean package -DskipTests

# Y una vez compilado, ejecutar el proyecto en el puerto 8080
FROM eclipse-temurin:21-jdk-jammy
COPY --from=build /target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]