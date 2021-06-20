#!/bin/bash
clear
java -jar -Dport=12081 -Dlogging.config=log4j2.xml -Dspring.config.location=application.yml target/pakar-oauth-server.jar
