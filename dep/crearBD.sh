#!/bin/bash

# Paramos y borramos la versión anterior del portal, si existe
sudo docker stop sisinf-postgresql
sudo docker rm sisinf-postgresql

# Creamos y arrancamos la imagen de postgres
sudo docker run --restart=unless-stopped -d --name sisinf-postgresql \
    --network=sisinf-ecoz \
    -e POSTGRES_USER=sisinf \
    -e POSTGRES_PASSWORD=sisinf \
    -e POSTGRES_DB=sisinf  \
    -p 5432:5432 \
    bitnami/postgresql:latest

# Copiamos ficheros sql de la carpeta postgresql y serán ejecutados
sudo docker cp postgresql sisinf-postgresql:/docker-entrypoint-initdb.d
