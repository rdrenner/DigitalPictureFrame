#!/bin/bash

DIR=`dirname "$BASH_SOURCE[0]"`
cd "$DIR"
echo "Current dir: $DIR"
java -Dlog4j.configurationFile=conf/log4j2-debug.xml -jar DigitalPictureFrame-0.0.1-Alpha.jar &