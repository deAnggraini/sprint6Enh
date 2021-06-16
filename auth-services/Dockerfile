FROM openjdk:11
#ENV APP_FILE auth-services.jar

#MAINTAINER jumono
EXPOSE 9094
COPY target/auth-services.jar auth-services.jar
ENTRYPOINT ["java","-jar","auth-services.jar"]