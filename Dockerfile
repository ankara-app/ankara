FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
ARG APP_DIR=/opt/ankara
COPY ${JAR_FILE} $APP_DIR/ankara.jar
WORKDIR $APP_DIR
ENTRYPOINT ["sh", "-c","java ${JAVA_OPTS} -jar ./ankara.jar"]