#!/bin/bash

PORTAL_PATH=$HOME/sisinf
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

# Paramos y borramos la versión anterior del portal, si existe
sudo docker stop sisinf-postgresql
sudo docker rm sisinf-postgresql

# Creamos los directorios necesarios
sudo rm -rf $PORTAL_PATH/postgresql
sudo mkdir $PORTAL_PATH/postgresql
sudo chmod 777 $PORTAL_PATH/postgresql

# Creamos y arrancamos la imagen de postgres
sudo docker run --restart=unless-stopped -d --name sisinf-postgresql \
    -e POSTGRES_USER=sisinf \
    -e POSTGRES_PASSWORD=sisinf \
    -e POSTGRES_DB=sisinf  \
    -v $PORTAL_PATH/postgresql:/bitnami/postgresql \
    -p 5432:5432 \
    bitnami/postgresql:latest

# Copiamos ficheros sql de la carpeta postgresql y serán ejecutados
sudo docker cp postgresql sisinf-postgresql:/docker-entrypoint-initdb.d
