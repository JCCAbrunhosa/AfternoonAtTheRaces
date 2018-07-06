package Monitors.bettingCenter;

import ServerProtocols.BettinCenterProtocol;
import comInf.PortsCom;
import comInf.ServerCom;
import genclass.GenericIO;

import java.net.ServerSocket;
import java.net.Socket;
/**
 *  Server for receiving messages sent by clients - Betting Center
 */

public class ServerBettingCenter extends Thread{

    private BettingCenterInterface bettingCenterInterface;

    /**
     * Initialize the server thread
     * @param bettingCenterInterface
     */
    public ServerBettingCenter(BettingCenterInterface bettingCenterInterface){
        this.bettingCenterInterface=bettingCenterInterface;
    }

    /**
     * Initializes the Server thread
     */
    @Override
    public void run() {

        ServerCom serverCom, serverComInput;
        ServerSocket serverSocket=null;
        Socket cSocket=null;
        ProcessThread bettingThread;
        int portNumber = 4000; //Placeholder

        BettinCenterProtocol bettinCenterProtocol;

        //Creates new communication channel
        serverCom=new ServerCom(PortsCom.portBettingCenter);
        serverCom.start();

        //Creates a new Protocol to process the input message
        bettinCenterProtocol = new BettinCenterProtocol(bettingCenterInterface);
        GenericIO.writelnString("Service Betting Center has been established!");
        GenericIO.writelnString("Server listening.");


        //Create a thread for each new input message, giving it the comm channel and the respective protocol for this server
        while(true){
            serverComInput= serverCom.accept();
            bettingThread = new ProcessThread(serverComInput, bettinCenterProtocol);
            bettingThread.start();
        }


    }
}
