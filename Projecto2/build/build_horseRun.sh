#!/bin/bash

rm -r Projecto2
rm -r __MACOSX
unzip -qq Projecto2.zip
cd Projecto2
javac -cp \* src/Monitors/bettingCenter/*.java src/Monitors/controlCenter/*.java src/Monitors/paddock/*.java src/Monitors/racingTrack/*.java src/Monitors/repository/*.java src/Monitors/stable/*.java src/comInf/*.java src/comInf/messages/*.java src/Entities/*.java src/Extras/*.java src/ServerProtocols/*.java src/Races2.java

cd src
java Races2
