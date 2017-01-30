#!/usr/bin/env bash

docker \
  run \
  --rm \
  --env 'ACCEPT_EULA=Y' \
  --env 'SA_PASSWORD=yourStrong(!)Password' \
  --name sqlserver2016 \
  --publish 1433:1433 \
  microsoft/mssql-server-linux