#!/bin/bash
docker build -t ms-demo-04-persistence-product-composite-service --platform linux/amd64 .
docker images | grep product-composite-service
