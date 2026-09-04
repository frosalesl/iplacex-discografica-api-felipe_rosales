#Stage 1: Construcción
FROM gradle:8.5-jdk21 AS builder

WORKDIR /app

#Copiar archivos de Gradle y código fuente
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src ./src

#Dar permisos al wrapper y compilar el fat-jar sin tests
RUN chmod +x ./gradlew
RUN ./gradlew bootJar -x test --no-daemon

#Stage 2: Ejecución
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

#Copiar el JAR generado desde la carpeta build/libs/
COPY --from=builder /app/build/libs/discografia-1.jar app.jar

#Render asigna el puerto mediante la variable PORT
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
