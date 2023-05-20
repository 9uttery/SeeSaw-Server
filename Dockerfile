FROM openjdk:11
EXPOSE 8080
ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} app.jar
VOLUME ["/var/log"]
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","-Duser.timezone=Asia/Seoul","/app.jar"]