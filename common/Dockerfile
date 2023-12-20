FROM adoptopenjdk:17-jdk-hotspot

ARG JAR_FILE=common/build/libs/*.jar

COPY ${JAR_FILE} common.jar

EXPOSE 5000

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/common.jar"]