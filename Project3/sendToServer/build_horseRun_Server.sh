#!/bin/bash

rm -r Project3
rm -r __MACOSX
unzip -qq Project3.zip
cd Project3/src/
echo "Compiling!"
./script.sh

echo "Initiating Server!"
cd dir_serverSide/
./serverSide.sh
