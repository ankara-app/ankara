#!/usr/bin/env bash
cd ~/Projects/ankara/ankara
sh ~/Projects/ankara/ankara/script/build.sh
cf login -a https://api.run.pivotal.io -u bonifacechacha@gmail.com -p grace1987
cf push