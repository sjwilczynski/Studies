#!/bin/bash
sudo su
apt-get update
apt-get install -y nginx php5-fpm php5-mysql

chown -hr www-data:www-data /usr/share/nginx/html
chmod -R g+rw /usr/share/nginx/html