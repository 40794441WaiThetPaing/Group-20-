FROM openjdk:latest
COPY ./target/group20-0.1.0.1-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "group20-0.1.0.1-jar-with-dependencies.jar"]