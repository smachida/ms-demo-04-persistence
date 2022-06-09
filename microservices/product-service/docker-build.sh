#!/bin/bash
docker build -t ms-demo-04-persistence-product-service .
docker images | grep product-service
