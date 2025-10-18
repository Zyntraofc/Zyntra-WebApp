# Usando uma imagem oficial do Maven com JDK
FROM maven:3.9.0-eclipse-temurin-17 AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos do projeto
COPY pom.xml .
COPY src ./src

# Build do projeto
RUN mvn clean package -DskipTests

# Segunda etapa: rodar o jar
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copia o jar construído
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta (ajuste conforme seu app)
EXPOSE 8080

# Comando para rodar o app
CMD ["java", "-jar", "app.jar"]
