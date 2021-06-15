FROM openjdk:11
ENV APP_FILE pakar-wf-server.jar

#MAINTAINER jumono
EXPOSE 12083
COPY target/${APP_FILE} ${APP_FILE}
ENTRYPOINT ["java","-jar","-Dspring.config.location=application.yml","pakar-wf-server.jar"]