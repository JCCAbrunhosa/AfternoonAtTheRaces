package Monitors.stable;

import ServerProtocols.StableProtocol;
import comInf.messages.Message;
import comInf.ServerCom;
import comInf.messages.MessageException;
import genclass.GenericIO;

import java.util.ArrayList;

/**
 * Defines the server-side thread that actually delivers the service
 */
public class ProcessThread extends Thread{
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
     *
     */
    private StableProtocol protocol;

    /**
     * Constructor to lunch a thread
     * @param serverCom
     * @param protocol
     */
    public ProcessThread(ServerCom serverCom, StableProtocol protocol) {
        //super("Thread:" + getThreadId());

        this.serverCom=serverCom;
        this.protocol=protocol;
    }


    /**
     * Initialize the thread to handle with the receive message - Thread life cycle
     */
    @Override
    public void run(){
        Message msg = null;
        ArrayList<Object> outMsg = null;

        msg = (Message) serverCom.readObject();
        try{
            outMsg = protocol.processInput(msg);
        }catch (MessageException e) {
            GenericIO.writelnString("Thread " + getName() + ": " + e.getMessage() + "!");
            GenericIO.writelnString(e.getMessageVal().toString());
            System.exit(1);
        }

        msg.setMsgType(msg.getMsgType());
        msg.setArgs(outMsg);

        //Visto ser o repositório e dependendo do pedido ele vai ser enviado para o cliente
        serverCom.writeObject(msg);      //enviar messagem apra o cliente
        serverCom.close();                  //fechar canal de comunicação

    }

    /**
     * Gets thread ID
     * @return id
     */
    private static int getThreadId() {
        Class<ProcessThread> cl = null;             // representação do tipo de dados ClientProxy na máquina
        //   virtual de Java
        int threadId;                                         // identificador da instanciação

        try {
            cl = (Class<ProcessThread>) Class.forName("ProcessThread");
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
