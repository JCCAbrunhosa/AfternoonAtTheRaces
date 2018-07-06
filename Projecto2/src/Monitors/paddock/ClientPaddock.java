package Monitors.paddock;

import comInf.ClientCom;
import comInf.messages.Message;
import comInf.messages.MsgType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for sending messages between the betting center and the server
 */

public class ClientPaddock implements PaddockInterface {

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
     * Constructor that will creates the remote paddock
     * @param hostName
     * @param portNumber
     */
    public ClientPaddock(String hostName, int portNumber){
        this.hostName=hostName;
        this.portNumber=portNumber;
    }

    @Override
    public int goCheckHorses(int specId) {
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
        Message toSend = new Message(MsgType.GOCHECKHORSES,null);
        ArrayList<Object> args = new ArrayList<>();
        args.add(0,specId);
        toSend.setArgs(args);

        com.writeObject(toSend);


        int winningChance;
        do{
            toSend = (Message) com.readObject();
            winningChance = (int) toSend.getArgs().get(0);
        }while(toSend.getMsgType() != MsgType.GOCHECKHORSES);

        com.close();
        return winningChance;
    }


    @Override
    public void proceedToPaddock(int horseId, double horseWinningChance) {
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
        Message toSend = new Message(MsgType.PROCEEDTOPADDOCK,null);
        ArrayList<Object> args = new ArrayList<>();
        args.add(0,horseId);
        args.add(1,horseWinningChance);
        toSend.setArgs(args);

        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.PROCEEDTOPADDOCK);


        com.close();

    }

    @Override
    public HashMap getWinningChances() {
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
        Message toSend = new Message(MsgType.GETWINNINGCHANCES);
        com.writeObject(toSend);


        HashMap<Integer,Integer> winningChances;
        do{
            toSend = (Message) com.readObject();
            winningChances = (HashMap<Integer,Integer>)toSend.getArgs().get(0);
        }while(toSend.getMsgType() != MsgType.GETWINNINGCHANCES);


        return winningChances;
    }

}
