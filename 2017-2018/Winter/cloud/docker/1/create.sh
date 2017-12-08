#!/usr/bin/env bash

docker build -t myhaproxy ./haproxy/
docker network create mynetwork
docker run -d  --net mynetwork --name web1 nginx
docker run -d  --net mynetwork --name web2 nginx
docker run -d -p 8060:80 --net mynetwork --name myhap myhaproxy
