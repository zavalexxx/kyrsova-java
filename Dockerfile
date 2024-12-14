FROM openjdk:17-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} eschool-app.jar
ENTRYPOINT ["java","-jar","/eschool-app.jar"]