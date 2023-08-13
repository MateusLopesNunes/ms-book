# Use a imagem base do OpenJDK 17
FROM adoptopenjdk/openjdk17:alpine

# Defina o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copie o arquivo pom.xml para o diretório de trabalho
COPY pom.xml .

# Execute o Maven para baixar as dependências do projeto
RUN mvn verify clean --fail-never

# Copie o restante do código-fonte para o diretório de trabalho
COPY src ./src

# Execute a construção do projeto com o Maven
RUN mvn package -DskipTests

# Use an official MySQL image as the database
FROM mysql:latest

# Set environment variables for MySQL
ENV MYSQL_ROOT_PASSWORD=root123
ENV MYSQL_DATABASE=reviewBook

# Expose the MySQL port
EXPOSE 3306

# Copy the SQL script to create the database into the MySQL container
COPY init.sql /docker-entrypoint-initdb.d/

# Comando para iniciar o MySQL (aqui você inicia o MySQL, não o app Spring)
CMD ["mysqld"]
