FROM gradle:7-jdk11 AS builder

COPY --chown=gradle:gradle . /app/src
WORKDIR /app/src
RUN gradle build -x test

FROM openjdk:11

WORKDIR /app
COPY --from=builder /app/src/build/libs/*.jar /app/app.jar

CMD ["java","-jar","/app/app.jar"]