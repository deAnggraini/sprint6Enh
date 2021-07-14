FROM openjdk:11
ENV APP_FILE pakar-wf-server.jar

COPY application.yml /config/application.yml
COPY log4j2.xml /config/log4j2.xml

EXPOSE 12083
COPY target/${APP_FILE} ${APP_FILE}

ENTRYPOINT ["java","-Dport=12083","-Dlogging.config=file:/config/log4j2.xml","-Dspring.profiles.active=dev-local","-Dspring.config.location=file:/config/application.yml","-jar", "pakar-wf-server.jar"]