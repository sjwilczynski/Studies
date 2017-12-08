#!/usr/bin/env bash

docker build -t 160312345/my_app_flask .
docker run -d -p 8090:5000 -e FOO="DEFAULT_VALUE_OF_FOO" --name app 160312345/my_app_flask