FROM openjdk:8-jdk-alpine
RUN addgroup -S ankara && adduser -S ankara -G ankara
USER ankara:ankara
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} ankara.jar
ENTRYPOINT ["java","-jar","/ankara.jar"]