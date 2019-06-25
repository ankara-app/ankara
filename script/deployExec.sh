#!/usr/bin/env bash
sh ~/Projects/ankara/ankara/script/build.sh
cd  ~/Projects/ankara/ankara/build/libs
rsync -v -e ssh ankara.war root@getankara.com:/var/ankara/ankara.war --progress
ssh root@getankara.com "sh scripts/restartAnkara.sh"