#!/bin/bash
docker build -t ms-demo-04-persistence-recommendation-service --platform linux/amd64 .
docker images | grep recommendation-service
