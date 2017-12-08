#!/usr/bin/env bash

docker stop web1 web2 myhap
docker rm web1 web2 myhap
docker network rm mynetwork