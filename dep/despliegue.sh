#!/bin/bash

# Instala docker
sudo apt install docker docker.io

# Arranca docker y se añade el arranque en el inicio del sistema
sudo systemctl start docker
sudo systemctl enable docker

# Crear red ecoz para conectar los contenedores entre sí
sudo docker network create --driver bridge sisinf-ecoz

./crearBD.sh
./crearWeb.sh
