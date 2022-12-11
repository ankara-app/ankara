#!/usr/bin/env bash
cd ~/projects/ankara/dev/ankara
./gradlew clean
./gradlew vaadinCompile
./gradlew vaadinThemeCompile
./gradlew vaadinUpdateWidgetset