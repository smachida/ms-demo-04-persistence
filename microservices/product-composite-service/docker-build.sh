#!/bin/bash
docker build -t ms-demo-04-persistence-product-composite-service .
docker images | grep product-composite-service
