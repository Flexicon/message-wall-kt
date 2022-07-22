FROM gradle:7-jdk11 AS builder

COPY --chown=gradle:gradle . /app/src
WORKDIR /app/src
RUN ./gradlew bootJar -x generateJooqClasses

# =============================================================================
FROM eclipse-temurin:17-jre AS RUNNER

WORKDIR /app
COPY --from=builder /app/src/build/libs/message-wall.jar /app/app.jar

CMD ["java","-jar","/app/app.jar"]