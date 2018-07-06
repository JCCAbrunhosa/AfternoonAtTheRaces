#!/bin/bash

echo "-------- An afternoon at the races --------"
echo " Sistemas Distribuídos 2018 - P3G07"
echo "João Abrunhosa nº 65245, Tatiana Moura nº 68048"
echo "RMI Project"

echo "Copying source code to the machines..."

sshpass -p "sdraces07" scp Project3.zip sd0307@l040101-ws01.ua.pt:~
sleep 1

sshpass -p "sdraces07" scp Project3.zip sd0307@l040101-ws02.ua.pt:~
sleep 1

sshpass -p "sdraces07" scp Project3.zip sd0307@l040101-ws03.ua.pt:~
sleep 1

sshpass -p "sdraces07" scp Project3.zip sd0307@l040101-ws04.ua.pt:~
sleep 1

sshpass -p "sdraces07" scp Project3.zip sd0307@l040101-ws05.ua.pt:~
sleep 1

sshpass -p "sdraces07" scp Project3.zip sd0307@l040101-ws06.ua.pt:~
sleep 1

sshpass -p "sdraces07" scp Project3.zip sd0307@l040101-ws07.ua.pt:~
sleep 1

sshpass -p "sdraces07" scp Project3.zip sd0307@l040101-ws08.ua.pt:~
sleep 1

sshpass -p "sdraces07" scp Project3.zip sd0307@l040101-ws09.ua.pt:~
sleep 1

echo "Starting to Build Code..."
echo "Compiling & Cleaning Source code..."
sshpass -p "sdraces07" ssh -o StrictHostKeyChecking=no sd0307@l040101-ws01.ua.pt 'bash -s' < build_horseRun_Registry.sh & PID_Logging=$!
sleep 5
sshpass -p "sdraces07" ssh -o StrictHostKeyChecking=no sd0307@l040101-ws01.ua.pt 'bash -s' < build_horseRun_Server.sh & PID_Logging=$!
sleep 5

sshpass -p "sdraces07" ssh -o StrictHostKeyChecking=no sd0307@l040101-ws02.ua.pt 'bash -s' < build_horseRun_Server.sh & PID_Logging=$!
sleep 5

sshpass -p "sdraces07" ssh -o StrictHostKeyChecking=no sd0307@l040101-ws03.ua.pt 'bash -s' < build_horseRun_Server.sh & PID_Logging=$!
sleep 5

sshpass -p "sdraces07" ssh -o StrictHostKeyChecking=no sd0307@l040101-ws04.ua.pt 'bash -s' < build_horseRun_Server.sh & PID_Logging=$!
sleep 5

sshpass -p "sdraces07" ssh -o StrictHostKeyChecking=no sd0307@l040101-ws05.ua.pt 'bash -s' < build_horseRun_Server.sh & PID_Logging=$!
sleep 5

sshpass -p "sdraces07" ssh -o StrictHostKeyChecking=no sd0307@l040101-ws06.ua.pt 'bash -s' < build_horseRun_Server.sh & PID_Logging=$!
sleep 5

sshpass -p "sdraces07" ssh -o StrictHostKeyChecking=no sd0307@l040101-ws07.ua.pt 'bash -s' < build_horseRun_Client.sh & PID_Logging=$!
sleep 5

sshpass -p "sdraces07" ssh -o StrictHostKeyChecking=no sd0307@l040101-ws08.ua.pt 'bash -s' < build_horseRun_Client.sh & PID_Logging=$!
sleep 5

sshpass -p "sdraces07" ssh -o StrictHostKeyChecking=no sd0307@l040101-ws09.ua.pt 'bash -s' < build_horseRun_Client.sh & PID_Logging=$!
sleep 5

sleep 5



echo ""
echo ""


#Save log
sshpass -p "sdraces07" scp -o StrictHostKeyChecking=no sd0307@l040101-ws02.ua.pt:/home/sd0307/Project3/Races.log .
