FROM openjdk:11
MAINTAINER barisaslan.com
ARG JAR_FILE
COPY ${JAR_FILE} reading-is-good.jar
ENTRYPOINT ["java", "-jar", "reading-is-good.jar"]