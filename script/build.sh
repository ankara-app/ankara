#!/usr/bin/env bash
cd ~/projects/ankara/dev/ankara
./gradlew clean
./gradlew vaadinThemeCompile
./gradlew assemble