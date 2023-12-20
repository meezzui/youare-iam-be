FROM azul/zulu-openjdk:17

ARG JAR_FILE=common/build/libs/*.jar

COPY ${JAR_FILE} common.jar

EXPOSE 5000

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "/common.jar"]