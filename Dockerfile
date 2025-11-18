FROM eclipse-temurin:18
COPY ./target/group20-0.1.0.2-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "group20-0.1.0.2-jar-with-dependencies.jar"]