# Stage 1: Build do projeto com Maven
FROM maven:3.9.0-eclipse-temurin-17 AS build

# Diretório de trabalho
WORKDIR /app

# Copia o pom.xml e a pasta src
COPY pom.xml .
COPY src ./src

# Gera a build (WAR ou estrutura de webapp)
RUN mvn clean package -DskipTests

# Stage 2: Rodar no Tomcat
FROM tomcat:10.1-jdk17

# Limpa as aplicações default do Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia a build gerada para o Tomcat
COPY --from=build /app/target/Zyntra-WebApp-1.0-SNAPSHOT /usr/local/tomcat/webapps/ROOT

# Expõe a porta padrão do Tomcat
EXPOSE 8080

# Comando para rodar o Tomcat
CMD ["catalina.sh", "run"]
