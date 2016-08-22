#!/usr/bin/env bash
cd ..
gradle assemble
cf login -a https://api.run.pivotal.io
cf push