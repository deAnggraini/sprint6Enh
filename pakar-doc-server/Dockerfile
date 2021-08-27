FROM openjdk:11
ENV APP_FILE pakar-doc-server.jar

#MAINTAINER jumono
#RUN mkdir /config
#RUN mkdir /images
#RUN mkdir /images/menus
#RUN mkdir /images/structures
#RUN mkdir /images/articles
#RUN mkdir /images/themes
#RUN mkdir /images/users

COPY application.yml /config/application.yml
COPY log4j2.xml /config/log4j2.xml
COPY messages.properties messages.properties
COPY messages_en.properties messages_en.properties
EXPOSE 12082

COPY target/${APP_FILE} ${APP_FILE}

ENTRYPOINT ["java","-Dport=12082","-Dlogging.config=file:/config/log4j2.xml","-Dspring.profiles.active=dev-local","-Dspring.config.location=file:/config/application.yml","-jar","pakar-doc-server.jar"]