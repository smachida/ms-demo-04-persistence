#!/bin/bash
docker build -t ms-demo-04-persistence-recommendation-service .
docker images | grep recommendation-service
