# ETAPA 1: BUILD

FROM gradle:8.5-jdk21 AS builder

WORKDIR /app

# Copiar archivos necesarios para la compilación
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copiar código fuente
COPY src ./src

# Compilar el JAR sin ejecutar tests
RUN chmod +x ./gradlew
RUN gradle clean bootJar -x test --no-daemon


# ETAPA 2: RUN

FROM openjdk:21

WORKDIR /app

# Copiar únicamente el JAR generado
COPY --from=builder /app/build/libs/discografia-1.jar app.jar

# Puerto de Spring Boot
ENV PORT=8080
EXPOSE 8080

# Ejecutar aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]