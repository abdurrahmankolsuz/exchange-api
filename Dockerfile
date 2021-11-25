FROM openjdk:8-jdk-alpine
EXPOSE 8080
ARG JAR_FILE=target/spring-boot-jpa-h2-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} spring-boot-jpa-h2.jar
ENTRYPOINT ["java","-jar","/spring-boot-jpa-h2.jar"]