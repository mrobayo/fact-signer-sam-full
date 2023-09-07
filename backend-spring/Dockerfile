FROM amazoncorretto:11-alpine-jdk
VOLUME /tmp
COPY target/factsigner-1.0.1-SNAPSHOT.jar factsigner.jar
EXPOSE 8081
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/factsigner.jar"]
