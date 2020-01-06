#!/usr/bin/env bash
set -x
echo "Connect database on localhost:3307 (instead of default 3306) to connect to the server's mysql database."
ssh -i 2019augPrivateAWSKey.pem -L 3307:localhost:3306 ubuntu@moov.progmasters.hu
