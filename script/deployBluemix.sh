#!/usr/bin/env bash
cd ~/Projects/ankara/ankara
sh ~/Projects/ankara/ankara/script/build.sh
cf login -a https://api.ng.bluemix.net -u bonifacechacha@gmail.com -p grace1987
cf push