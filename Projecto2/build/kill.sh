#!/bin/bash
# kill java process running

sshpass -p "sdraces07" ssh -o StrictHostKeyChecking=no sd0307@l040101-ws01.ua.pt 'bash -s' < processKill.sh & PID_Logging=$!
sleep 1

sshpass -p "sdraces07" ssh -o StrictHostKeyChecking=no sd0307@l040101-ws02.ua.pt 'bash -s' < processKill.sh & PID_Logging=$!
sleep 1

sshpass -p "sdraces07" ssh -o StrictHostKeyChecking=no sd0307@l040101-ws03.ua.pt 'bash -s' < processKill.sh & PID_Logging=$!
sleep 1

sshpass -p "sdraces07" ssh -o StrictHostKeyChecking=no sd0307@l040101-ws04.ua.pt 'bash -s' < processKill.sh & PID_Logging=$!
sleep 1

sshpass -p "sdraces07" ssh -o StrictHostKeyChecking=no sd0307@l040101-ws05.ua.pt 'bash -s' < processKill.sh & PID_Logging=$!
sleep 1

sshpass -p "sdraces07" ssh -o StrictHostKeyChecking=no sd0307@l040101-ws06.ua.pt 'bash -s' < processKill.sh & PID_Logging=$!
sleep 1

sshpass -p "sdraces07" ssh -o StrictHostKeyChecking=no sd0307@l040101-ws08.ua.pt 'bash -s' < processKill.sh & PID_Logging=$!
sleep 1

sshpass -p "sdraces07" ssh -o StrictHostKeyChecking=no sd0307@l040101-ws09.ua.pt 'bash -s' < processKill.sh & PID_Logging=$!
sleep 1

sshpass -p "sdraces07" ssh -o StrictHostKeyChecking=no sd0307@l040101-ws10.ua.pt 'bash -s' < processKill.sh & PID_Logging=$!
sleep 1