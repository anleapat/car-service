#!/bin/sh
set -x
ls

REGISTRY_URL=$1
IMAGE=$2
TAG=$3

sudo docker build -t ${IMAGE}:${TAG} .

IMAGE_ID=`sudo docker images | grep ${IMAGE} | grep ${TAG} | awk -F ' ' '{print $3}'`
IMAGE_URL=$REGISTRY_URL/${IMAGE}:${TAG}
sudo docker tag ${IMAGE_ID} $IMAGE_URL
sudo docker push $IMAGE_URL

sudo docker rmi -f $IMAGE_ID
