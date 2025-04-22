#!/bin/bash

docker rm -f scala_play_crud 2>/dev/null
docker build -t scala-play-crud .
docker run -d --rm -p 9000:9000 --name scala_play_crud scala-play-crud

echo "Waiting for starting app..."
sleep 15

ngrok http 9000
