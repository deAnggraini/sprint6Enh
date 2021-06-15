FROM openjdk:11
#ENV APP_FILE pakar-oauth-server.jar

#MAINTAINER jumono
EXPOSE 12081
COPY target/pakar-oauth-server.jar pakar-oauth-server.jar
ENTRYPOINT ["java","-jar","-Dspring.config.location=application.yml","pakar-oauth-server.jar"]