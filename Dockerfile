FROM eclipse-temurin:21-alpine
WORKDIR app
COPY src src
COPY .mvn .mvn
COPY mvnw mvnw
COPY mvnw.cmd mvnw.cmd
COPY pom.xml pom.xml
RUN chmod +x mvnw && ./mvnw install && \
    mv ./target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
EXPOSE 8080