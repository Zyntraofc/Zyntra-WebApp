# Stage único usando Tomcat com JDK 17
FROM tomcat:10.1-jdk17

# Limpa a webapp padrão do Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia sua aplicação para o Tomcat
# Ajuste o caminho se o nome da pasta target mudar
COPY target/Zyntra-WebApp-1.0-SNAPSHOT /usr/local/tomcat/webapps/ROOT

# Expõe a porta padrão do Tomcat
EXPOSE 8080

# Comando padrão para rodar o Tomcat
CMD ["catalina.sh", "run"]

