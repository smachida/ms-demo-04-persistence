#!/bin/bash

HARBOR_HOST=harbor-prod.mp-tanzu-demo.com

echo "pushing the images to the registry: $HARBOR_HOST"
docker login $HARBOR_HOST

docker tag ms-demo-04-persistence-product-service $HARBOR_HOST/ms-demo/ms-demo-04-persistence-product-service
docker push $HARBOR_HOST/ms-demo/ms-demo-04-persistence-product-service
docker tag ms-demo-04-persistence-recommendation-service $HARBOR_HOST/ms-demo/ms-demo-04-persistence-recommendation-service
docker push $HARBOR_HOST/ms-demo/ms-demo-04-persistence-recommendation-service
docker tag ms-demo-04-persistence-review-service $HARBOR_HOST/ms-demo/ms-demo-04-persistence-review-service
docker push $HARBOR_HOST/ms-demo/ms-demo-04-persistence-review-service
docker tag ms-demo-04-persistence-product-composite-service $HARBOR_HOST/ms-demo/ms-demo-04-persistence-product-composite-service
docker push $HARBOR_HOST/ms-demo/ms-demo-04-persistence-product-composite-service
