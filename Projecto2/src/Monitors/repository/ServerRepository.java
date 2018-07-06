package Monitors.repository;

import ServerProtocols.RepositoryProtocol;
import comInf.PortsCom;
import comInf.ServerCom;
import genclass.GenericIO;

/**
 *  Server for receiving messages sent by clients - Repository
 */

public class ServerRepository extends Thread{

    private RepositoryInterface repositoryInterface;

    /**
     * Initialize the server thread
     * @param repositoryInterface
     */
    public ServerRepository(RepositoryInterface repositoryInterface){
        this.repositoryInterface=repositoryInterface;
    }

    /**
     * Initializes the Server thread
     */
    @Override
    public void run(){

        ServerCom serverCom, serverComInput;
        ProcessThread repoThread;


        RepositoryProtocol repositoryProtocol;

        //Creates new communication channel
        serverCom=new ServerCom(PortsCom.portRepository);
        serverCom.start();

        //Creates a new Protocol to process the input message
        repositoryProtocol = new RepositoryProtocol(repositoryInterface);
        GenericIO.writelnString("Service Repository has been established!");
        GenericIO.writelnString("Server listening.");


        //Create a thread for each new input message, giving it the comm channel and the respective protocol for this server
        while(true){
            serverComInput= serverCom.accept();
            repoThread = new ProcessThread(serverComInput, repositoryProtocol);
            repoThread.start();
        }


    }
}
