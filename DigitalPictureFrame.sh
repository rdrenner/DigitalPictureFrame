#!/bin/bash

DIR=`dirname "$BASH_SOURCE[0]"`
cd "$DIR"
echo "Current dir: $DIR"
java -jar DigitalPictureFrame.jar &