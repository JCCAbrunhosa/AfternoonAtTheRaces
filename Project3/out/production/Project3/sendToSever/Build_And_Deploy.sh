#!/bin/bash

echo "-------- An afternoon at the races --------"
echo " Sistemas Distribuídos 2018 - P3G07"
echo "João Abrunhosa nº 65245, Tatiana Moura nº 68048"
echo "RMI Project"

echo "Copying source code to the machines..."
sshpass -p "sdraces07" scp Project3.zip sd0307@l040101-ws01.ua.pt:~
sleep 1
#
echo ""
echo ""


echo "Starting to Build Code..."
echo "Compiling & Cleaning Source code..."
sshpass -p "sdraces07" ssh -o StrictHostKeyChecking=no sd0307@l040101-ws01.ua.pt 'bash -s' < build_horseRun.sh & PID_Logging=$!
sleep 5


#Save log
sshpass -p "sdraces07" scp -o StrictHostKeyChecking=no sd0307@l040101-ws01.ua.pt:/home/sd0307/Project3/Races.log .
