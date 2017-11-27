#!/usr/bin/env bash
cd ..
gradle clean
gradle assemble
cd  build/libs
rsync -v -e ssh ankara.war root@getankara.com:/var/ankara/ankara.war --progress
ssh root@getankara.com "sh scripts/restartAnkara.sh"