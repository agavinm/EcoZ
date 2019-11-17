#!/bin/bash

# Paramos y borramos la versión anterior del portal, si existe
sudo docker stop sisinf-tomcat
sudo docker rm sisinf-tomcat

# Creamos y arrancamos la imagen de tomcat
sudo docker run --restart=unless-stopped -d --name sisinf-tomcat \
    --network=sisinf-ecoz \
    -p 8080:8080 \
    tomcat:9.0.27-jdk11-adoptopenjdk-hotspot

# Copiamos los scripts que serán ejecutados
sudo docker cp tomcat/context.xml sisinf-tomcat:/usr/local/tomcat/conf
sudo docker cp tomcat/server.xml sisinf-tomcat:/usr/local/tomcat/conf
sudo docker cp tomcat/tomcat-users.xml sisinf-tomcat:/usr/local/tomcat/conf
sudo docker cp tomcat/*.war sisinf-tomcat:/usr/local/tomcat/webapps
sudo docker cp tomcat/context-privilege.xml sisinf-tomcat:/usr/local/tomcat/webapps/manager/META-INF/context.xml
sudo docker cp tomcat/context-privilege.xml sisinf-tomcat:/usr/local/tomcat/webapps/host-manager/META-INF/context.xml
