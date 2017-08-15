#!/usr/bin/env bash
cd ..
gradle clean assemble
cf login -a https://api.ng.bluemix.net -u bonifacechacha@gmail.com -p grace1987
cf push