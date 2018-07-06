#!/bin/bash

rm -r Project3
rm -r __MACOSX
unzip -qq Project3.zip
cd Project3/src/
./script.sh

cd registry 
java ServerRegisterRemoteObject
cd ..
cd Main
java ServerMain
java ClientMain
