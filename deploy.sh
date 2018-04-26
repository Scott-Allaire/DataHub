#!/usr/bin/env bash
docker build -t iot-hub .
docker run -p 3000:3000 -d iot-hub
