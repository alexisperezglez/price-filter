FROM maven:3.9-amazoncorretto-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM amazoncorretto:17
WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]