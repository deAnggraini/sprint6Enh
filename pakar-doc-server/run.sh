#!/bin/bash
clear
java -jar -Dport=12082 -Dlogging.config=log4j2.xml -Dspring.profiles.active=dev-local -Dspring.config.location=application.yml target/pakar-doc-server.jar
