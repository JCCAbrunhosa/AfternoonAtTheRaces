package Monitors.bettingCenter;

import comInf.ClientCom;
import comInf.messages.Message;
import comInf.messages.MsgType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for sending messages between the betting center and the server
 */

public class ClientBettingCenter implements BettingCenterInterface {

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
     * Constructor that will creates the remote betting center.
     * @param hostName IP address
     * @param portNumber port used to send messages
     */
    public ClientBettingCenter(String hostName, int portNumber){
        this.hostName=hostName;
        this.portNumber=portNumber;
    }

    /**
     * Method to call the function acceptTheBets on the server,
     * with the appropriate parameters
     */
    @Override
    public void acceptTheBets() {
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
        toSend = new Message(MsgType.ACCEPTTHEBETS, null);
        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.ACCEPTTHEBETS);

        com.close();
    }


    /**
     *
     *  Method to call the function placeBets on the server,
     *  with the appropriate parameters
     * @param specId
     * @param betValue
     * @param horseId
     * @param possibleGain
     */
    @Override
    public void placeABet(int specId, double betValue, int horseId, double possibleGain) {
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
        ArrayList<Object> args = new ArrayList<>();
        args.add(0,specId);
        args.add(1,betValue);
        args.add(2,horseId);
        args.add(3,possibleGain);
        toSend=new Message(MsgType.PLACEABET, args);
        com.writeObject(toSend);

        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.PLACEABET);

        com.close();
    }

    /**
     * Method to call the function goCollectTheGains on the server
     * Receives from the server and returns the value function
     * @param specId
     * @return
     */
    @Override
    public double goCollectTheGains(int specId) {
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
        ArrayList<Object> args = new ArrayList<>();
        args.add(0,specId);
        toSend=new Message(MsgType.GOCOLLECTTHEGAINS, args);
        com.writeObject(toSend);

        double gains;
        do{
            toSend = (Message) com.readObject();
            gains = (double) toSend.getArgs().get(0);
        }while(toSend.getMsgType() != MsgType.GOCOLLECTTHEGAINS);

        com.close();
        return gains;
    }

    /**
     *  Method to call the function honourTheBets on the server,
     *  with the appropriate parameters
     * @param winnersList
     */
    @Override
    public void honourTheBets(ArrayList<Integer> winnersList) {
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
        ArrayList<Object> args = new ArrayList<>();
        args.add(0,winnersList);
        toSend = new Message(MsgType.HONOURTHEBETS, args);
        com.writeObject(toSend);

        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.HONOURTHEBETS);

        com.close();

    }

    /**
     *  Method to call the function areThereAnyWinners on the server
     *  Receives from the server and returns the value function
     * @param winnerHorse
     * @return
     */
    @Override
    public HashMap areThereAnyWinners(int winnerHorse) {
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
        ArrayList<Object> args = new ArrayList<>();
        args.add(0,winnerHorse);
        toSend = new Message(MsgType.ARETHEREANYWINNERS, args);
        com.writeObject(toSend);


        HashMap<Integer, Integer> winnersList;
        do{
            toSend = (Message) com.readObject();
            winnersList= (HashMap<Integer, Integer>)toSend.getArgs().get(0);;
        }while(toSend.getMsgType() != MsgType.ARETHEREANYWINNERS);

        com.close();
        return winnersList;
    }

    /**
     *  Method to call the function areSpectatorsReadyToBet on the server,
     *  with the appropriate parameters
     */
    @Override
    public void areSpectatorsReadyToBet() {
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
        toSend = new Message(MsgType.ARESPECSREADYTOBET, null);
        com.writeObject(toSend);

        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.ARESPECSREADYTOBET);


        com.close();
    }
}
