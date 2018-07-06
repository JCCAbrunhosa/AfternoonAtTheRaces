package Monitors.stable;

import comInf.ClientCom;
import comInf.messages.Message;
import comInf.messages.MsgType;

import java.util.ArrayList;

/**
 * Class for sending messages between the betting center and the server
 */

public class ClientStable implements StableInterface {

    /**
     * Ip address
     */
    String hostName;
    /**
     * Port num to send messages
     */
    int portNumber;

    /**
     * Message to send
     */
    Message toSend=null;

    /**
     * Constructor that will creates the remote stable
     * @param hostName
     * @param portNumber
     */
    public ClientStable(String hostName, int portNumber){
        this.hostName=hostName;
        this.portNumber=portNumber;
    }

    @Override
    public void summonHorsesToPaddock() {
        ClientCom com = new ClientCom(hostName,portNumber);
        while(!com.open()){
            //
            //write message and waits for the reply
            try {
                Thread.sleep((long) 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        toSend = new Message(MsgType.SUMMONHORSESTOPADDOCK, null);
        com.writeObject(toSend);

        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.SUMMONHORSESTOPADDOCK);
        com.close();
    }

    @Override
    public void proceedToStable(int horseId) {
        ClientCom com = new ClientCom(hostName,portNumber);
        while(!com.open()){
            //
            //write message and waits for the reply
            try {
                Thread.sleep((long) 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        toSend = new Message(MsgType.PROCEEDTOSTABLE, null);
        ArrayList<Object> args = new ArrayList<>();
        args.add(0,horseId);
        toSend.setArgs(args);

        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.PROCEEDTOSTABLE);
        com.close();
    }
}
