package Monitors.stable;

import Monitors.repository.RepositoryInterface;
import ServerProtocols.StableProtocol;
import comInf.PortsCom;
import comInf.ServerCom;
import genclass.GenericIO;

/**
 *  Server for receiving messages sent by clients - Stable
 */

public class ServerStable extends Thread{

    private StableInterface stableInterface;

    /**
     * Initialize the server thread
     * @param stableInterface
     */
    public ServerStable (StableInterface stableInterface){
        this.stableInterface=stableInterface;
    }


    /**
     * Initializes the Server thread
     */
    @Override
    public void run() {

        ServerCom serverCom, serverComInput;
        ProcessThread stableThread;

        //String repository = PortsCom.machineRepository;

        StableProtocol stableProtocol;

        //First create the repository (falta uma classe para escrever o log file);
        //Stable stable = new Stable(reposi);

        //Creates new communication channel
        serverCom=new ServerCom(PortsCom.portStable);
        serverCom.start();

        //Creates a new Protocol to process the input message
        stableProtocol = new StableProtocol(stableInterface);
        GenericIO.writelnString("Service Stable has been established!");
        GenericIO.writelnString("Server listening.");


        //Create a thread for each new input message, giving it the comm channel and the respective protocol for this server
        while(true){
            serverComInput= serverCom.accept();
            stableThread = new ProcessThread(serverComInput, stableProtocol);
            stableThread.start();
        }



    }
}
