package Monitors.bettingCenter;

import ServerProtocols.BettinCenterProtocol;
import comInf.messages.Message;
import comInf.ServerCom;
import comInf.messages.MessageException;
import genclass.GenericIO;

import java.util.ArrayList;

/**
 * Defines the server-side thread that actually delivers the service
 */

public class ProcessThread extends Thread {

    /**
     *
     * Count num threads
     * @serialField  nrThreads
     */
    private static  int nThreads;

    /**
     * communication channel
     *
     * @serialField serverCom
     */
    private ServerCom serverCom;

    /**
    */
    private BettinCenterProtocol protocol;

    /**
     * Constructor to lunch a thread
     * @param serverCom
     * @param protocol
     */
    public ProcessThread(ServerCom serverCom, BettinCenterProtocol protocol){
        this.serverCom=serverCom;
        this.protocol=protocol;
    }


    /**
     * Initialize the thread to handle with the receive message - Thread life cycle
     */
    @Override
    public void run(){
        super.run();

        Message msg = (Message) serverCom.readObject();
        ArrayList<Object> outMsg = null;
        try {
            outMsg = protocol.processInput(msg);
        } catch (MessageException e) {
            e.printStackTrace();
        }

        msg.setMsgType(msg.getMsgType());
        msg.setArgs(outMsg);

        //Visto ser o repositório e dependendo do pedido ele vai ser enviado para o cliente
        serverCom.writeObject(msg);
        serverCom.close();
    }

    /**
     * Gets thread ID
     * @return id
     */
    private static int getThreadId() {
        Class<Monitors.paddock.ProcessThread> cl = null;             // representação do tipo de dados ClientProxy na máquina
        //   virtual de Java
        int threadId;                                         // identificador da instanciação

        try {
            cl = (Class<Monitors.paddock.ProcessThread>) Class.forName("ProcessThread");
        } catch (ClassNotFoundException e) {
            GenericIO.writelnString(" Thread wasn't find!");
            e.printStackTrace();
            System.exit(1);
        }

        synchronized (cl) {
            threadId = nThreads;
            nThreads += 1;
        }

        return threadId;
    }
}
