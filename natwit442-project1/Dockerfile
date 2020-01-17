FROM maven:3.6.0-jdk-8-slim AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package

FROM openjdk:8-alpine
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/natwit442-project1-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "natwit442-project1-0.0.1-SNAPSHOT.jar"]


