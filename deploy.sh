#!/bin/sh
set -x

NAMESPACE=$1
IMAGE=$2
APPLICATION=$3
REPLICAS=$4

#replace params
sed -i '''s/replicas_param/'$REPLICAS'/''' car-service.yaml
sed -i '''s/tag_param/'$IMAGE'/''' car-service.yaml

IS_EXISTS=`kubectl get deployment/$APPLICATION -n $NAMESPACE | grep $APPLICATION`

DEPLOY_PARAM=""
if [ "X$IS_EXISTS" != "X" ]; then
  DEPLOY_PARAM="--record"
fi

kubectl apply -f car-service.yaml $DEPLOY_PARAM