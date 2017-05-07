#!/bin/bash

## Custom build script to build the project in a docker container image

cd $(readlink -f $(dirname $0)/../../..)

if test -f target/chess-club-*.jar
then
	echo "Target build already present, skipping build"
else
	./mvnw -Ppostgresql -DskipTests=true clean package
fi

docker build -t chesscorp/chess-club .
