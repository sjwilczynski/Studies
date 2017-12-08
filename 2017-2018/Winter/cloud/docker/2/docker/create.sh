#!/usr/bin/env bash

docker run -d -p 8081:5000 -e FOO="DEFAULT_VALUE_OF_FOO" --name app1 160312345/my_app_flask
docker run -d -p 8082:5000 -e FOO="NON_DEFAULT_VALUE_OF_FOO" --name app2 160312345/my_app_flask
docker run -d -p 8083:5000 -e FOO="STUPID_VALUE" --name app3 160312345/my_app_flask