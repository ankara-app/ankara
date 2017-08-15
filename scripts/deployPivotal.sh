#!/usr/bin/env bash
cd ..
gradle clean assemble
cf login -a https://api.run.pivotal.io -u bonifacechacha@gmail.com -p grace1987
cf push