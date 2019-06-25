#!/usr/bin/env bash
cd ~/Projects/ankara/ankara
gradle clean
gradle vaadinCompile
gradle vaadinThemeCompile
gradle vaadinUpdateWidgetset