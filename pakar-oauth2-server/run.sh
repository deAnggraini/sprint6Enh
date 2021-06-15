#!/bin/bash
clear
java -jar -Dport=12081 -Dspring.config.location=application.yml target/pakar-oauth-server.jar
