#!/usr/bin/env bash
set -x
set -e
cd moovsmart
ng build --prod="true"
cd ..
mvn package
chmod 600 2019augPrivateAWSKey.pem
scp -i 2019augPrivateAWSKey.pem target/*.war ubuntu@moov.progmasters.hu:/home/ubuntu
sleep 1
ssh -i 2019augPrivateAWSKey.pem ubuntu@moov.progmasters.hu "/home/ubuntu/restart.sh"


