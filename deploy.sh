#!/bin/sh
set -x

WORKSPACE=$1
IMAGE=$2
APPLICATION=$3
REPLICAS=$4

LINE=`kubectl get deployment -n $WORKSPACE | grep ${APPLICATION} | wc -l`
if [ $LINE -gt 0 ]; then
	for POD in `kubectl get pod -n $WORKSPACE | grep $APPLICATION | awk -F ' ' '{print $1}'`
	do
		CONTAINER_NAME=`kubectl describe pod -n $WORKSPACE $POD | grep 'container' | head -1 | awk -F ' ' '{print $NF}'`
		kubectl set image deployment/${APPLICATION} -n $WORKSPACE $CONTAINER_NAME=$IMAGE $record
	done
else
	if [ "X$1" != "X"  ]; then
		DEPLOY_PARAM="--replicas=$REPLICAS"
	fi
	
	kubectl create deployment ${APPLICATION} -n $WORKSPACE --image=${IMAGE} $DEPLOY_PARAM
fi

