package Monitors.controlCenter;

import comInf.ClientCom;
import comInf.messages.Message;
import comInf.messages.MsgType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class for sending messages between the betting center and the server
 */

public class ClientCC implements ControlCenterInterface {

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
     * Constructor that will creates the remote control center
     * @param hostName
     * @param portNumber
     */
    public ClientCC(String hostName, int portNumber){
        this.hostName=hostName;
        this.portNumber=portNumber;
    }
    @Override
    public void waitForNextRace(int specId) {
        ClientCom com = new ClientCom(hostName, portNumber);
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
        Message toSend = new Message(MsgType.WAITFORNEXTRACE, args);

        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.WAITFORNEXTRACE);

        com.close();
    }

    @Override
    public void goWatchTheRace(int specId) {
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
        Message toSend = new Message(MsgType.GOWATCHTHERACE, args);

        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.GOWATCHTHERACE);


        com.close();

    }

    @Override
    public ArrayList reportResults(HashMap<Integer, Integer> betList, int winnerHorse) {
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
        args.add(0,betList);
        args.add(1, winnerHorse);
        Message toSend = new Message(MsgType.REPORTRESULTS, args);

        com.writeObject(toSend);

        ArrayList<Object> results;
        do{
            toSend = (Message) com.readObject();
            results = (ArrayList<Object>) toSend.getArgs().get(0);
        }while(toSend.getMsgType() != MsgType.REPORTRESULTS);


        com.close();


        return results;
    }

    @Override
    public void allHorsesOnPaddock() {
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
        Message toSend = new Message(MsgType.ALLHORSESONPADDOCK, null);

        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.ALLHORSESONPADDOCK);

        com.close();

    }

    @Override
    public boolean haveIWon(int specId) {
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
        Message toSend = new Message(MsgType.HAVEIWON, args);

        com.writeObject(toSend);

        boolean results;
        do{
            toSend = (Message) com.readObject();
            results = (boolean) toSend.getArgs().get(0);
        }while(toSend.getMsgType() != MsgType.HAVEIWON);


        com.close();


        return results;
    }

    @Override
    public void entertainTheGuests() {
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
        Message toSend = new Message(MsgType.ENTERTAINTHEGUESTS, null);

        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.ENTERTAINTHEGUESTS);

        com.close();

    }

    @Override
    public void relaxABit(int specId, double finalValue) {
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
        args.add(1,finalValue);
        Message toSend = new Message(MsgType.RELAXABIT, args);

        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.RELAXABIT);

        com.close();
    }

    @Override
    public void allSpectatorsReady() {
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
        Message toSend = new Message(MsgType.ALLSPECTATORSREADY, null);

        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.ALLSPECTATORSREADY);

        com.close();
    }
}
