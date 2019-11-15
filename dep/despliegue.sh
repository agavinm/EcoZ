#!/bin/bash

PORTAL_PATH=$HOME/sisinf
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

# Instala docker
sudo apt install docker docker.io

# Arranca docker y se añade el arranque en el inicio del sistema
sudo systemctl start docker
sudo systemctl enable docker

# Creamos los directorios necesarios
sudo rm -rf $PORTAL_PATH
sudo mkdir $PORTAL_PATH
sudo chmod 755 $PORTAL_PATH

./crearBD.sh
./crearWeb.sh
