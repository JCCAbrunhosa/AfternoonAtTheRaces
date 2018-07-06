package clientSide;

import extras.PortsCom;
import interfaces.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  This data type instantiates and registers a remote object that will run mobile code.
 *  Communication is based in Java RMI.
 */

public class ClientMain {
    /**
     * Stable's interface
     *
     * @serialField stableInterface
     */
    public static StableInterface stableInterface;

    /**
     * Repository's interface
     *
     * @serialField repositoryInterface
     */
    public static RepositoryInterface repositoryInterface;

    /**
     * Racing Track's interface
     *
     * @serialField racingTrackInterface
     */
    public static RacingTrackInterface racingTrackInterface;

    /**
     * Paddock's interface
     *
     * @serialField paddockInterface
     */
    public static PaddockInterface paddockInterface;

    /**
     * Control Center's interface
     *
     * @serialField controlCenterInterface
     */
    public static ControlCenterInterface controlCenterInterface;

    /**
     * Betting Center's interface
     *
     * @serialField bettingCenterInterface
     */
    public static BettingCenterInterface bettingCenterInterface;

    /**
     * Registry
     *
     * @serialField registry
     */
    public static Registry registry;

    /**
     * RMI listening port
     *
     * @serialField rmiPort
     */
    public static int rmiPort;

    /**
     * RMI name
     *
     * @serialField rmiName
     */
    public static String rmiName;

    //Initiate variables
    /**
     * Number of horses
     *
     * @serialField nrHorses
     */
    public static int nrHorses = 4;

    /**
     * Number of spectators
     *
     * @serialField nrSpectators
     */
    public static int nrSpectators = 4;

    /**
     * Horse
     *
     * @serialField horse
     */
    public static Horse horse = null;

    /**
     * Spectator
     *
     * @serialField spectator
     */
    public static Spectator spectator = null;

    /**
     * List of Spectators
     *
     * @serialField listSpectator
     */
    public static ArrayList<Spectator> listSpectator = new ArrayList<>();

    /**
     * List of Horses
     *
     * @serialField listHorse
     */
    public static ArrayList<Horse> listHorse = new ArrayList<>();

    /**
     * Random initialization
     *
     * @serialField rand
     */
    public static Random rand = new Random();

    /**
     * Horse winning chance
     *
     * @serialField winningChance
     */
    public static double winningChance = 0;

    /**
     * Total chances
     *
     * @serialField totalChance
     */
    public static double totalChance = 1.0;



    /**
     * Broker
     *
     * @serialField broker
     */
    public static Broker broker=null;
    /**
     *  Main task.
     */
    public static void main(String[] args) throws RemoteException, NotBoundException {

        //Get registry
        rmiPort=PortsCom.rmiPort;
        rmiName=PortsCom.rmiName;


        registry = LocateRegistry.getRegistry(rmiName,rmiPort);
        System.out.println("Registry Located!");
        repositoryInterface=(RepositoryInterface) registry.lookup("Repository");
        System.out.println("Repository Located!");
        stableInterface=(StableInterface) registry.lookup("Stable");
        System.out.println("Stable located!");
        racingTrackInterface=(RacingTrackInterface) registry.lookup("RacingTrack");
        System.out.println("RacingTrack Located!");
        paddockInterface=(PaddockInterface) registry.lookup("Paddock");
        System.out.println("Paddock Located!");
        controlCenterInterface=(ControlCenterInterface) registry.lookup("ControlCenter");
        System.out.println("CC Located!");
        bettingCenterInterface=(BettingCenterInterface) registry.lookup("BettingCenter");
        System.out.println("BettingCenter Located!");

        startClients();


        //terminar clientes

        for (int i = 0; i < listSpectator.size(); i++) {
            try {
                spectator = listSpectator.get(i);
                spectator.join();

            } catch (InterruptedException ex) {
            }
        }

        for (int i = 0; i < listHorse.size(); i++) {
            try {
                horse = listHorse.get(i);
                horse.join();
            } // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            catch (InterruptedException ex) {
            }
        }
        try {
            broker.join();
            System.err.print("MANAGER VAI FECHAR");
        } catch (InterruptedException ex) {
        }
        System.exit(0);

    }
    /**
     *  Start Clients
     */

    public static void startClients() throws RemoteException {
        try {
            //Then we create the threads

            InetAddress brokerIP = null;
            try{
                brokerIP = InetAddress.getByName(PortsCom.machineBorker);
            }catch (UnknownHostException e) {
                e.printStackTrace();
            }
            if (NetworkInterface.getByInetAddress(brokerIP) != null){
              broker = new Broker(racingTrackInterface, stableInterface, bettingCenterInterface, controlCenterInterface, paddockInterface, repositoryInterface);
                broker.getPriority();
                broker.start();
            }



            InetAddress horsesIP = null;
            try{
                horsesIP = InetAddress.getByName(PortsCom.machineHorse);
            }catch (UnknownHostException e) {
                e.printStackTrace();
            }
            if (NetworkInterface.getByInetAddress(horsesIP) != null) {
                for (int i = 1; i <= nrHorses; i++) {
                    rand = new Random();
                    winningChance = 0 + (rand.nextDouble() * (totalChance - 0));
                    totalChance = totalChance - winningChance;
                    if (i == nrHorses)
                        winningChance = totalChance;
                    System.out.println(i + " has winning change of " + winningChance);

                    int maxDistance = 1 + rand.nextInt((10 - 1) + 1);//Distance varies between 1 and 10
                    horse = new Horse(i, 0, new Random().nextInt(maxDistance), Math.abs(maxDistance), winningChance, stableInterface, paddockInterface, racingTrackInterface, repositoryInterface);
                    listHorse.add(horse);
                    horse.start();
                }
            }

            InetAddress specsIP = null;
            try{
                specsIP = InetAddress.getByName(PortsCom.machineSpectator);
            }catch (UnknownHostException e) {
                e.printStackTrace();
            }
            if (NetworkInterface.getByInetAddress(specsIP) != null) {
                for (int j = 1; j <= nrSpectators; j++) {
                    spectator = new Spectator(j, 400, paddockInterface, bettingCenterInterface, controlCenterInterface, repositoryInterface);
                    listSpectator.add(spectator);
                    spectator.start();
                }
            }
        }catch (Exception e){
            Logger.getLogger(ClientMain.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
