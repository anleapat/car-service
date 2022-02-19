#!/bin/sh
set -x
pwd
ls

WORKSPACE=parking
TAG=`date "+%Y%m%d%H%M%S-%N"`
APPLICATION=car-service
REGISTRY_URL=192.168.56.231:32098

docker build -t ${APPLICATION}:${TAG} .

IMAGE_ID=`docker images | grep ${APPLICATION} | grep ${TAG} | awk -F ' ' '{print $3}'`
docker tag ${IMAGE_ID} $REGISTRY_URL/${APPLICATION}:${TAG}
docker push $REGISTRY_URL/${APPLICATION}:${TAG}

docker rmi -f `docker images -f "label=application=${APPLICATION}" -q | sort -u`

LINE=`kubectl get deployment -n $WORKSPACE | grep ${APPLICATION} | wc -l`
if [ $LINE -gt 0 ]; then
	for POD in `kubectl get pod -n $WORKSPACE | grep $APPLICATION | awk -F ' ' '{print $1}'`
	do
		CONTAINER_NAME=`kubectl describe pod -n $WORKSPACE $POD | grep 'container' | head -1 | awk -F ' ' '{print $NF}'`
		kubectl set image deployment/${APPLICATION} -n $WORKSPACE $CONTAINER_NAME=$REGISTRY_URL/${APPLICATION}:${TAG} --record
	done
else
	if [ "X$1" != "X"  ]; then
		DEPLOY_PARAM="--replicas=$1"
	fi
	
	kubectl create deployment ${APPLICATION} -n $WORKSPACE --image=$REGISTRY_URL/${APPLICATION}:${TAG} $DEPLOY_PARAM
fi

