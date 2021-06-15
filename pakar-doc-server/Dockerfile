FROM openjdk:11
ENV APP_FILE pakar-doc-server.jar

#MAINTAINER jumono
EXPOSE 12082
COPY target/${APP_FILE} ${APP_FILE}
ENTRYPOINT ["java","-jar","pakar-doc-server.jar"]