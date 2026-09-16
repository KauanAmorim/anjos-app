# Estágio 1: Build da aplicação (Usando JDK)
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app

# Copiar os arquivos essenciais para resolver as dependências primeiro (otimiza o cache do Docker)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# Dá permissão de execução pro Maven Wrapper
RUN chmod +x mvnw
# Faz o Docker verificar se o pom.xml mudou para reinstalar as dependências. Se não, usa o cache para agilizar o processo.
RUN ./mvnw dependency:go-offline

# Copia o código fonte e faz o build (pulando testes para ser mais rápido na imagem)
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Estágio 2: Imagem final de execução (Usando apenas JRE para ficar leve)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia apenas o .jar gerado no estágio anterior
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta que o Spring Boot usa
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
