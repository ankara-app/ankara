#!/usr/bin/env bash
cd ..
#gradle clean
gradle assemble
cd  build/libs
rsync -v -e ssh ankara.war root@45.55.255.248:/var/lib/tomcat8/webapps/ROOT.war --progress
#ssh root@getankara.com "sh restartAnkara.sh"

