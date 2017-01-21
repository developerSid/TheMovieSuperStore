#!/usr/bin/env bash

docker \
  run \
  --rm \
  --env POSTGRES_PASSWORD=password \
  --env POSTGRES_USER=developer \
  --env POSTGRES_DB=dev \
  --name postgres9.6.1 \
  --publish 5432:5432 \
  postgres:9.6.1-alpine
