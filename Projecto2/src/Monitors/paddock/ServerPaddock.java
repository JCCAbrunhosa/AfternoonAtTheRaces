package Monitors.paddock;

import ServerProtocols.PaddockProtocol;
import comInf.PortsCom;
import comInf.ServerCom;
import genclass.GenericIO;

/**
 *  Server for receiving messages sent by clients -Paddock
 */

public class ServerPaddock extends Thread{

    private PaddockInterface paddockInterface;

    /**
     * Initialize the server thread
     * @param paddockInterface
     */
    public ServerPaddock(PaddockInterface paddockInterface){
        this.paddockInterface=paddockInterface;
    }

    /**
     * Initializes the Server thread
     */
    @Override
    public void run() {

        ServerCom serverCom, serverComInput;
        ProcessThread paddockThread;

        PaddockProtocol paddockProtocol;

        //First create the repository (falta uma classe para escrever o log file);
        //Paddock paddock = new Paddock(repository);

        //Creates new communication channel
        serverCom=new ServerCom(PortsCom.portPaddock);
        serverCom.start();

        //Creates a new Protocol to process the input message
        paddockProtocol = new PaddockProtocol(paddockInterface);
        GenericIO.writelnString("Service Paddock has been established!");
        GenericIO.writelnString("Server listening.");


        //Create a thread for each new input message, giving it the comm channel and the respective protocol for this server
        while(true){
            serverComInput= serverCom.accept();
            paddockThread = new ProcessThread(serverComInput, paddockProtocol);
            paddockThread.start();
        }

    }

}
