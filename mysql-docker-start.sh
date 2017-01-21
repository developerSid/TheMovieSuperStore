#!/usr/bin/env bash

docker \
  run \
  --rm \
  --env MYSQL_ROOT_PASSWORD=password \
  --env MYSQL_USER=developer \
  --env MYSQL_PASSWORD=password \
  --env MYSQL_DATABASE=dev \
  --name mysql57 \
  --publish 3306:3306 \
  mysql:5.7