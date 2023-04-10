FROM gradle:7.6.1-jdk17-alpine as cache
WORKDIR /app
ENV GRADLE_USER_HOME /cache
COPY build.gradle.kts settings.gradle.kts ./
RUN gradle --no-daemon build --stacktrace

FROM gradle:7.6.1-jdk17-alpine as build
ENV APP_HOME=/app
WORKDIR $APP_HOME
COPY --from=cache /cache /home/gradle/.gradle
COPY build.gradle.kts settings.gradle.kts ./
COPY src/ src/

RUN gradle --no-daemon clean bootJar

FROM openjdk:17-jdk-slim as backend
ENV APP_HOME=/app

WORKDIR $APP_HOME
COPY --from=build $APP_HOME/build/libs/*.jar ./app.jar

ENTRYPOINT exec java -jar app.jar