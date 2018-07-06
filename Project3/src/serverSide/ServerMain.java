package serverSide;


import extras.PortsCom;
import interfaces.*;
import interfaces.Register;
import genclass.GenericIO;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;
import java.util.Random;

/**
 *  This data type instantiates and registers a remote object that will run mobile code.
 *  Communication is based in Java RMI.
 */

public class ServerMain {
    /**
     *  Main task.
     */
    public static void main(String[] args) throws RemoteException, SocketException, AlreadyBoundException {

        //Initiate variables
        Properties prop = new Properties();
        int nrHorses = 4;
        int nrSpectators = 4;
        int nrRaces = 4;
        Random rand = new Random();
        int raceDistance = 10 + rand.nextInt((95 - 10) + 1);//Distance varies between 10 and 95;


        /* get location of the registry service */

        String rmiRegHostName =PortsCom.rmiName;
        int rmiPort = PortsCom.rmiPort;

        // create and install the security manager
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
        GenericIO.writelnString("Security manager was installed!");

        /* register it with the general registry service */
        String nameEntryBase = "RegisterHandler";
        Registry registry = null;
        Register reg = null;

        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiPort);
        } catch (RemoteException e) {
            GenericIO.writelnString("RMI registry creation exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        GenericIO.writelnString("RMI registry was created!");

        try {
            reg = (Register) registry.lookup(nameEntryBase);
        } catch (RemoteException e) {
            GenericIO.writelnString("RegisterRemoteObject lookup exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            GenericIO.writelnString("RegisterRemoteObject not bound exception: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }


        //First create the repository (falta uma classe para escrever o log file);
        RepositoryInterface repository = null;
        InetAddress repositoryIP = null;
        try {
            repositoryIP = InetAddress.getByName(PortsCom.machineRepository);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (NetworkInterface.getByInetAddress(repositoryIP) != null) {
            repository = new Repository(nrHorses, nrSpectators, nrRaces, new Random().nextInt(raceDistance), "", reg);
            repository = (RepositoryInterface) UnicastRemoteObject.exportObject(repository, PortsCom.portRepository);

            if (repository != null)
                reg.bind("Repository", repository);

            System.out.print("\n Maquina Repositorio Local Registada");

        } else {
            try {
                repository = (RepositoryInterface) registry.lookup("Repository");
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        }


        //Create the BettingCenter
        BettingCenterInterface bettingCenter=null;
        InetAddress bettingCenterIP = null;
        try{
            bettingCenterIP = InetAddress.getByName(PortsCom.machineBettingCenter);
        }catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (NetworkInterface.getByInetAddress(bettingCenterIP) != null){
            bettingCenter = new BettingCenter(repository,reg);
            bettingCenter = (BettingCenterInterface) UnicastRemoteObject.exportObject(bettingCenter, PortsCom.portBettingCenter);
            if (bettingCenter != null)
                reg.bind("BettingCenter", bettingCenter);

            System.out.print("\n Maquina BettingCenter Local Registada");

        }


        //Create the ControlCenter
        ControlCenterInterface controlCenter = null;
        InetAddress controlCenterIP = null;
        try{controlCenterIP = InetAddress.getByName(PortsCom.machineControlCenter);
        }catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (NetworkInterface.getByInetAddress(controlCenterIP) != null){
            controlCenter = new ControlCenter(repository,reg);
            controlCenter = (ControlCenterInterface) UnicastRemoteObject.exportObject(controlCenter, PortsCom.portControlCenter);
            if (controlCenter != null)
                reg.bind("ControlCenter", controlCenter);

            System.out.print("\n Maquina ControlCenter Local Registada");

        }


        //Create the Paddock
        PaddockInterface paddock = null;
        InetAddress paddockIP = null;
        try{
            paddockIP = InetAddress.getByName(PortsCom.machinePaddock);
        }catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if(NetworkInterface.getByInetAddress(paddockIP)!=null){
            paddock = new Paddock(repository,reg);
            paddock = (PaddockInterface) UnicastRemoteObject.exportObject(paddock, PortsCom.portPaddock);
            if (paddock != null)
                reg.bind("Paddock", paddock);

            System.out.print("\n Maquina Paddock Local Registada");

        }



        //Create the Racing Track
        RacingTrackInterface racingTrack = null;
        InetAddress racingTrackIP = null;
        try {
            racingTrackIP = InetAddress.getByName(PortsCom.machineRacingTrack);
        }catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if(NetworkInterface.getByInetAddress(racingTrackIP) != null){
            racingTrack = new RacingTrack(repository,reg);
            racingTrack = (RacingTrackInterface) UnicastRemoteObject.exportObject(racingTrack, PortsCom.portRacingTrack);
            if (racingTrack != null)
                reg.bind("RacingTrack", racingTrack);

            System.out.print("\n Maquina RacingTrack Local Registada");

        }

        //Create the Stable
        StableInterface stable = null;
        InetAddress stableIP = null;
        try{
            stableIP = InetAddress.getByName(PortsCom.machineStable);
        }catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if(NetworkInterface.getByInetAddress(stableIP)!=null){
            stable = new Stable(repository,reg);
            stable = (StableInterface) UnicastRemoteObject.exportObject(stable, PortsCom.portStable);
            if (stable != null)
                reg.bind("Stable", stable);

            System.out.print("\n Maquina Stable Local Registada");

        }

    }

}
