FROM openjdk:11
#ENV APP_FILE auth-services.jar

#MAINTAINER jumono
EXPOSE 9094

RUN mkdir /config
RUN mkdir /var/bca
RUN mkdir /var/bca/file

COPY application.yml /config/application.yml
COPY log4j2.xml /config/log4j2.xml
COPY user.json /var/bca/file/user.json

COPY target/auth-services.jar auth-services.jar

ENTRYPOINT ["java","-Dport=12085","-Dlogging.config=/config/log4j2.xml","-Dspring.profiles.active=dev-local","-Dspring.config.location=file:/config/application.yml","-jar","auth-services.jar"]