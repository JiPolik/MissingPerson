FROM amazoncorretto:17
#COPY target/*.jar src/main/docker
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} missing-person.jar
ENTRYPOINT ["java", "-jar", "missing-person.jar"]
