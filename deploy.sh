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
if [ "X$IS_EXISTS" != "X" ]; then
  kubectl delete -f car-service.yaml
fi

#deploy
kubectl create -f car-service.yaml
