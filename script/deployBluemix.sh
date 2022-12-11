#!/usr/bin/env bash
cd ~/projects/ankara/ankara
sh ~/projects/ankara/ankara/script/build.sh
cf login -a https://api.ng.bluemix.net -u bonifacechacha@gmail.com -p grace1987
cf push