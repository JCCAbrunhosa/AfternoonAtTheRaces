package Main;

import Entities.Broker;
import Entities.Horse;
import Entities.Spectator;
import Monitors.bettingCenter.BettingCenter;
import Monitors.bettingCenter.BettingCenterInterface;
import Monitors.bettingCenter.ClientBettingCenter;
import Monitors.bettingCenter.ServerBettingCenter;
import Monitors.controlCenter.ClientCC;
import Monitors.controlCenter.ControlCenter;
import Monitors.controlCenter.ControlCenterInterface;
import Monitors.controlCenter.ServerControlCenter;
import Monitors.paddock.ClientPaddock;
import Monitors.paddock.Paddock;
import Monitors.paddock.PaddockInterface;
import Monitors.paddock.ServerPaddock;
import Monitors.racingTrack.ClientRacingTrack;
import Monitors.racingTrack.RacingTrack;
import Monitors.racingTrack.RacingTrackInterface;
import Monitors.racingTrack.ServerRacingTrack;
import Monitors.repository.ClientRepository;
import Monitors.repository.Repository;
import Monitors.repository.RepositoryInterface;
import Monitors.repository.ServerRepository;
import Monitors.stable.ClientStable;
import Monitors.stable.ServerStable;
import Monitors.stable.Stable;
import Monitors.stable.StableInterface;
import comInf.PortsCom;

import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

/**
 * Main
 */

public class Races2 {

    public static void main(String[] args) throws SocketException, UnknownHostException {

        int nrHorses = 4;
        int nrSpectators = 4;
        int nrRaces = 4;
        Random rand = new Random();
        int raceDistance = 10 + rand.nextInt((95 - 10) + 1);//Distance varies between 10 and 95;

        //Initiate variables
        Horse horse = null;
        Spectator spectator = null;
        ArrayList<Spectator> listSpectator = new ArrayList<>();
        ArrayList<Horse> listHorse = new ArrayList<>();
        double winningChance = 0;
        double totalChance = 1.0;

        //First create the repository (falta uma classe para escrever o log file);
        RepositoryInterface repository = null;
        InetAddress repositoryIP = InetAddress.getByName(PortsCom.machineRepository);
        ServerRepository serverRepository;
        if (NetworkInterface.getByInetAddress(repositoryIP) != null) {
            //isRepository=true;
            //   System.err.print("\nESTA MAQUINA É REPO");
            repository = new Repository(nrHorses, nrSpectators, nrRaces, new Random().nextInt(raceDistance), "");
            serverRepository = new ServerRepository(repository);
            serverRepository.start();
        } else {
            repository = new ClientRepository(PortsCom.machineRepository, PortsCom.portRepository);
        }


        //Create the BettingCenter
        BettingCenterInterface bettingCenter=null;
        InetAddress bettingCenterIP = InetAddress.getByName(PortsCom.machineBettingCenter);
        ServerBettingCenter serverBettingCenter;
        if (NetworkInterface.getByInetAddress(bettingCenterIP) != null){
            bettingCenter = new BettingCenter(repository);
            serverBettingCenter = new ServerBettingCenter(bettingCenter);
            serverBettingCenter.start();
        }else{
            bettingCenter = new ClientBettingCenter(PortsCom.machineBettingCenter, PortsCom.portBettingCenter);
        }



        //Create the ControlCenter
        ControlCenterInterface controlCenter = null;
        InetAddress controlCenterIP = InetAddress.getByName(PortsCom.machineControlCenter);
        ServerControlCenter serverControlCenter;
        if (NetworkInterface.getByInetAddress(controlCenterIP) != null){
            controlCenter = new ControlCenter(repository);
            serverControlCenter = new ServerControlCenter(controlCenter);
            serverControlCenter.start();
        }else{
           controlCenter = new ClientCC(PortsCom.machineControlCenter, PortsCom.portControlCenter);
        }


        //Create the Paddock
        PaddockInterface paddock = null;
        InetAddress paddockIP = InetAddress.getByName(PortsCom.machinePaddock);
        ServerPaddock serverPaddock;
        if(NetworkInterface.getByInetAddress(paddockIP)!=null){
            paddock = new Paddock(repository);
            serverPaddock = new ServerPaddock(paddock);
            serverPaddock.start();
        }else{
            paddock = new ClientPaddock(PortsCom.machinePaddock, PortsCom.portPaddock);
        }


        //Create the Racing Track
        RacingTrackInterface racingTrack = null;
        InetAddress racingTrackIP = InetAddress.getByName(PortsCom.machineRacingTrack);
        ServerRacingTrack serverRacingTrack;
        if(NetworkInterface.getByInetAddress(racingTrackIP) != null){
            racingTrack = new RacingTrack(repository);
            serverRacingTrack = new ServerRacingTrack(racingTrack);
            serverRacingTrack.start();
        }else{
            racingTrack = new ClientRacingTrack(PortsCom.machineRacingTrack, PortsCom.portRacingTrack);
        }

        //Create the Stable
        StableInterface stable = null;
        InetAddress stableIP = InetAddress.getByName(PortsCom.machineStable);
        ServerStable serverStable;
        if(NetworkInterface.getByInetAddress(stableIP)!=null){
            stable = new Stable(repository);
            serverStable = new ServerStable(stable);
            serverStable.start();
        }else{
            stable = new ClientStable(PortsCom.machineStable, PortsCom.portStable);
        }


        InetAddress horseIP = InetAddress.getByName(PortsCom.machineHorse);
        if(NetworkInterface.getByInetAddress(horseIP)!=null){
            for (int i = 1; i <= nrHorses; i++) {
                rand = new Random();
                winningChance = 0 + (rand.nextDouble() * (totalChance - 0));
                totalChance = totalChance - winningChance;
                if (i == nrHorses)
                    winningChance = totalChance;
                System.out.println(i + " has winning change of " + winningChance);

                int maxDistance = 1 + rand.nextInt((10 - 1) + 1);//Distance varies between 1 and 10
                horse = new Horse(i, 0, new Random().nextInt(maxDistance), Math.abs(maxDistance), winningChance, stable, paddock, racingTrack, repository);
                listHorse.add(horse);
                horse.start();
            }
        }

        Broker broker=null;
        InetAddress brokerIP = InetAddress.getByName(PortsCom.machineBorker);
        if(NetworkInterface.getByInetAddress(brokerIP)!=null){
            broker = new Broker(racingTrack, stable, bettingCenter, controlCenter, paddock, repository);
            broker.getPriority();
            broker.start();
        }

        InetAddress spectatorsIP = InetAddress.getByName(PortsCom.machineSpectator);
        if(NetworkInterface.getByInetAddress(spectatorsIP)!=null){
            for (int j = 1; j <= nrSpectators; j++) {
                spectator = new Spectator(j, 400, paddock, bettingCenter, controlCenter, repository);
                listSpectator.add(spectator);
                spectator.start();
            }
        }


        while (true) {
            try {
                broker.join();

                System.exit(0);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
