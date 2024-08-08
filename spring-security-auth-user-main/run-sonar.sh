#!/bin/bash
echo \####################################;
echo \#  Running mvn sonar commande package \#;
echo \####################################;
mvn package;
#mvn verify sonar:sonar -Dsonar.projectKey=docker_toto -Dsonar.organization=spring-boot-tuto -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=7effee4435dd5b97f7f164aa2c881da280542a60;
