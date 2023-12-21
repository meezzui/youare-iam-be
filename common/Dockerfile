FROM azul/zulu-openjdk:17

ARG JAR_FILE=/build/libs/*.jar

COPY ${JAR_FILE} common.jar

EXPOSE 80:8080

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/common.jar"]