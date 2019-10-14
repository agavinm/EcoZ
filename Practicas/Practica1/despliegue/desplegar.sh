#!/bin/bash

# Instala docker
sudo apt install docker docker.io

# Arranca docker y se añade el arranque en el inicio del sistema
sudo systemctl start docker
sudo systemctl enable docker

# Se construye el servidor de apache desde el fichero Dockerfile y arranca en el puerto 8080
sudo docker build -t sisinfo-apache2 .
sudo docker run -dit --name sisinfo-webapp -p 8080:80 sisinfo-apache2

# Muestra los procesos de docker
# sudo docker ps

# Para el servidor de apache
# sudo docker stop sisinfo-webapp

# Elimina el contenedor del servidor de apache
# sudo docker rm sisinfo-webapp

# Elimina la imagen del servidor de apache
# sudo docker rmi sisinfo-apache2
