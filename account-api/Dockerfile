FROM maven:3.8-openjdk-11-slim as DEPENDENCIES
WORKDIR /opt/app
COPY pom.xml .
RUN mvn -B -e -C clean org.apache.maven.plugins:maven-dependency-plugin:3.3.0:go-offline

FROM maven:3.8-openjdk-11-slim as BUILDER
WORKDIR /opt/app
COPY --from=DEPENDENCIES /root/.m2 /root/.m2
COPY pom.xml .
COPY src/main/resources /opt/app/src/main/resources
COPY src/main/java /opt/app/src/main/java
RUN mvn -B -e -o clean package -DskipTests

FROM azul/zulu-openjdk-alpine:11-jre-headless
WORKDIR /opt/app
COPY --from=BUILDER /opt/app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=docker"]
