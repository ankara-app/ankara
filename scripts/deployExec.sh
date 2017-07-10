#!/usr/bin/env bash
cd ..
./gradlew clean
./gradlew assemble
cd  build/libs
rsync -v -e ssh ankara.war root@getankara.com:/var/ankara/ankara.war --progress
ssh root@getankara.com "sh restartAnkara.sh"