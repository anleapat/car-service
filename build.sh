#!/bin/sh
set -x
pwd
ls
mvn clean package -Dmaven.test.skip=true -U --settings settings.xml

VERSION=`date "+%Y%m%d-%H%M%S"`
PACKAGE=`ls target/*jar | awk -F '/' '{print $2}' | awk -F '-SNAPSHOT.jar' '{print $1}'`
docker build -t ${PACKAGE}:${VERSION} .

IMAGE_ID=`docker images | grep ${PACKAGE} | grep ${VERSION} | awk -F ' ' '{print $3}'`
docker tag ${IMAGE_ID} parking-registry-svc.parking:5000/${PACKAGE}:${VERSION}
docker push parking-registry-svc.parking:5000/${PACKAGE}:${VERSION}

kubectl create deploy ${PACKAGE} -n parking --image=parking-registry-svc.parking:5000/${PACKAGE}:${VERSION}
docker rmi -f `docker images -f "label=application=${PACKAGE}" -q`
