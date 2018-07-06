#!/bin/bash

rm -r Project3
rm -r __MACOSX
unzip -qq Project3.zip
cd Project3/src/
./script.sh


cd dir_registry/
./registry_com_alt.sh 22378
./registry.sh
