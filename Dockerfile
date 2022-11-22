FROM gradle:jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:17.0.2-jdk-slim
WORKDIR /home
COPY --from=build /home/gradle/src/build/libs/comments-0.0.1-SNAPSHOT.jar comments-0.0.1-SNAPSHOT.jar
EXPOSE 4001
ENTRYPOINT ["java", "-jar", "comments-0.0.1-SNAPSHOT.jar",  "--server.port=4001"]
