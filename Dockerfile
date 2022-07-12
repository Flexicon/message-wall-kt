FROM gradle:7-jdk11 AS builder

COPY --chown=gradle:gradle . /app/src
WORKDIR /app/src
RUN ./gradlew bootJar -x generateJooqClasses

FROM openjdk:11

WORKDIR /app
COPY --from=builder /app/src/build/libs/message-wall-0.0.1.jar /app/app.jar

CMD ["java","-jar","/app/app.jar"]