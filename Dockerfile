FROM openjdk:latest
COPY ./target/group20-1.0-SNAPSHOT-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "group20-1.0-SNAPSHOT-jar-with-dependencies.jar"]