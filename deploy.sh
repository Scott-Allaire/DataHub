#!/usr/bin/env bash
docker rmi -f iot-hub
docker build -t iot-hub .
docker run -p 3000:3000 -d iot-hub
