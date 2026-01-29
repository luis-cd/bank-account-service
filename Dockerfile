# Usamos la imagen oficial de Maven para construir el proyecto
FROM maven:3.9.2-eclipse-temurin-17 AS build

WORKDIR /app

COPY bank-account-service/pom.xml .
COPY bank-account-service/src ./src

RUN mvn clean test
RUN mvn clean package -DskipTests=false

FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]