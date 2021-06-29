FROM openjdk:11
#ENV APP_FILE pakar-oauth-server.jar
ARG JAR_FILE=target/pakar-oauth-server.jar
#MAINTAINER jumono
RUN mkdir /config

COPY application.yml /config/application.yml
COPY log4j2.xml /config/log4j2.xml

EXPOSE 12081
COPY target/pakar-oauth-server.jar pakar-oauth-server.jar
ENTRYPOINT ["java","-Dport=12081","-Dlogging.config=/config/log4j2.xml","-Dspring.profiles.active=dev-local","-Dspring.config.location=file:/config/application.yml","-jar","pakar-oauth-server.jar"]