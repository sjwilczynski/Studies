#!/bin/bash

sudo apt-get update && sudo apt-get install apache2 -y
echo '<!doctype html><html><body><h1>Generowane przez skrypt!</h1></body></html>' | sudo tee /var/www/html/index.html