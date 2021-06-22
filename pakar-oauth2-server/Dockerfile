FROM openjdk:11
#ENV APP_FILE pakar-oauth-server.jar
ARG JAR_FILE=target/pakar-oauth-server.jar
#MAINTAINER jumono
EXPOSE 12081
COPY ${JAR_FILE} pakar-oauth-server.jar
ENTRYPOINT ["java","-jar","Dport=12081"," -Dlogging.config=log4j2.xml","-Dspring.config.location=application.yml","pakar-oauth-server.jar"]