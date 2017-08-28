#!/bin/bash

mvn clean
rm -rf spring-app/src/main/resources/static/built
mvn install

cd spring-app
webpack

mvn spring-boot:run
