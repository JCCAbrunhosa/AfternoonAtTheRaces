package Monitors.controlCenter;

import ServerProtocols.ControlCenterProtocol;
import comInf.PortsCom;
import comInf.ServerCom;
import genclass.GenericIO;

/**
 *  Server for receiving messages sent by clients - Control Center
 */
public class ServerControlCenter extends Thread{

    private ControlCenterInterface controlCenterInterface;

    /**
     * Initialize the server thread
     * @param controlCenterInterface
     */
    public ServerControlCenter(ControlCenterInterface controlCenterInterface){
        this.controlCenterInterface=controlCenterInterface;
    }

    /**
     * Initializes the Server thread
     */
    @Override
    public void run(){

        ServerCom serverCom, serverComInput;
        ProcessThread paddockThread;

        String repository = PortsCom.machineRepository;

        ControlCenterProtocol controlProtocol;

        //First create the repository (falta uma classe para escrever o log file);
        //ControlCenter controlCenter = new ControlCenter(repository);

        //Creates new communication channel
        serverCom=new ServerCom(PortsCom.portControlCenter);
        serverCom.start();

        //Creates a new Protocol to process the input message
        controlProtocol = new ControlCenterProtocol(controlCenterInterface);
        GenericIO.writelnString("Service Control Center has been established!");
        GenericIO.writelnString("Server listening.");


        //Create a thread for each new input message, giving it the comm channel and the respective protocol for this server
        while(true){
            serverComInput= serverCom.accept();
            paddockThread = new ProcessThread(serverComInput, controlProtocol);
            paddockThread.start();
        }

    }
}


