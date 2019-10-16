#!/bin/bash

PORTAL_PATH=$HOME/sisinf-server
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

# Instala docker
sudo apt install docker docker.io

# Arranca docker y se añade el arranque en el inicio del sistema
sudo systemctl start docker
sudo systemctl enable docker

# Creamos los directorios necesarios
sudo rm -rf $PORTAL_PATH
sudo mkdir $PORTAL_PATH
sudo mkdir $PORTAL_PATH/tomcat
sudo chmod 755 $PORTAL_PATH
sudo chmod 777 $PORTAL_PATH/tomcat

# Paramos y borramos la versión anterior del portal, si existe
sudo docker stop sisinf-tomcat
sudo docker rm sisinf-tomcat

# Creamos y arrancamos la imagen de tomcat
sudo docker run --restart=unless-stopped -d --name sisinf-tomcat \
    -v $PORTAL_PATH/tomcat:/bitnami \
    -p 8080:8080 \
    bitnami/tomcat:latest

sleep 5
# Copiamos los ficheros de configuración en la carpeta compartida
sudo cp tomcat/context.xml $PORTAL_PATH/tomcat/tomcat/conf
sudo cp tomcat/server.xml $PORTAL_PATH/tomcat/tomcat/conf
sudo cp tomcat/tomcat-users.xml $PORTAL_PATH/tomcat/tomcat/conf
sudo cp tomcat/*.war $PORTAL_PATH/tomcat/tomcat/data
sudo cp tomcat/context-privilege.xml $PORTAL_PATH/tomcat/tomcat/data/manager/META-INF/context.xml
sudo cp tomcat/context-privilege.xml $PORTAL_PATH/tomcat/tomcat/data/host-manager/META-INF/context.xml
