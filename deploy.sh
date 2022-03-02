#!/bin/sh
set -x

NAMESPACE=$1
IMAGE=$2
APPLICATION=$3
REPLICAS=$4

LINE=`kubectl get deployment -n $NAMESPACE | grep ${APPLICATION} | wc -l`
if [ $LINE -gt 0 ]; then
	kubectl set image deployment/${APPLICATION} -n $NAMESPACE *=$IMAGE --record
	POD_NUMBER=`kubectl get deployment/$APPLICATION -n $NAMESPACE | grep $APPLICATION | awk -F ' ' '{print $4}'`
	if [ $POD_NUMBER -ne $REPLICAS ]; then
		kubectl scale -n $NAMESPACE deployment $APPLICATION --replicas=$REPLICAS
	fi
else
	if [ "X$1" != "X"  ]; then
		DEPLOY_PARAM="--replicas=$REPLICAS"
	fi
	
	kubectl create deployment ${APPLICATION} -n $NAMESPACE --image=${IMAGE} $DEPLOY_PARAM
fi

