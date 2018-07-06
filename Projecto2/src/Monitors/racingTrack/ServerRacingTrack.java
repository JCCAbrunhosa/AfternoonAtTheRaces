package Monitors.racingTrack;

import ServerProtocols.RacingTrackProtocol;
import comInf.PortsCom;
import comInf.ServerCom;
import genclass.GenericIO;

import java.net.ServerSocket;
import java.net.Socket;

/**
 *  Server for receiving messages sent by clients - Racing Track
 */

public class ServerRacingTrack extends Thread {

    private RacingTrackInterface racingTrackInterface;

    /**
     * Initialize the server thread
     * @param racingTrackInterface
     */
    public ServerRacingTrack(RacingTrackInterface racingTrackInterface){
        this.racingTrackInterface=racingTrackInterface;
    }


    /**
     * Initializes the Server thread
     */
    @Override
    public void run(){

        ServerCom serverCom, serverComInput;
        ServerSocket serverSocket=null;
        Socket cSocket=null;
        ProcessThread racingThread;
        int portNumber = 4000; //Placeholder

        RacingTrackProtocol racingTrackProtocol;

        //Creates new communication channel
        serverCom=new ServerCom(PortsCom.portRacingTrack);
        serverCom.start();

        //Creates a new Protocol to process the input message
        racingTrackProtocol = new RacingTrackProtocol(racingTrackInterface);
        GenericIO.writelnString("Service RacingTrack has been established!");
        GenericIO.writelnString("Server listening.");


        //Create a thread for each new input message, giving it the comm channel and the respective protocol for this server
        while(true){
            serverComInput= serverCom.accept();
            racingThread = new ProcessThread(serverComInput, racingTrackProtocol);
            racingThread.start();
        }

    }

}
