#!/bin/bash
docker build -t ms-demo-04-persistence-review-service .
docker images | grep review-service
