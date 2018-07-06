package Monitors.racingTrack;

import comInf.ClientCom;
import comInf.messages.Message;
import comInf.messages.MsgType;

import java.util.ArrayList;

/**
 * Class for sending messages between the betting center and the server
 */

public class ClientRacingTrack implements RacingTrackInterface {

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
     * Constructor that will creates the remote racing track
     * @param hostName
     * @param portNumber
     */
    public ClientRacingTrack(String hostName, int portNumber){
        this.hostName=hostName;
        this.portNumber=portNumber;
    }

    @Override
    public void proceedToStartLine(int horseId) {
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
        Message toSend = new Message(MsgType.PROCEEDTOSTARTLINE, null);
        ArrayList<Object> args = new ArrayList<>();
        args.add(0,horseId);
        toSend.setArgs(args);

        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.PROCEEDTOSTARTLINE);

        com.close();
    }

    @Override
    public void startRace() {
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
        Message toSend = new Message(MsgType.STARTRACE, null);

        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.STARTRACE);

        com.close();
    }

    @Override
    public void makeAMove(int horseId, int maxDistance) {
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
        Message toSend = new Message(MsgType.MAKEAMOVE, null);
        ArrayList<Object> args = new ArrayList<>();
        args.add(0,horseId);
        args.add(1,maxDistance);
        toSend.setArgs(args);

        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.MAKEAMOVE);

        com.close();
    }

    @Override
    public boolean hasFinishingLineBeenCrossed(int horseId) {
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
        Message toSend = new Message(MsgType.HASFINISHINGLINEBEENCROSSED, null);
        ArrayList<Object> args = new ArrayList<>();
        args.add(0,horseId);
        toSend.setArgs(args);

        com.writeObject(toSend);

        boolean hasFinished;
        do{
            toSend = (Message) com.readObject();
           hasFinished = (boolean)toSend.getArgs().get(0);
        }while(toSend.getMsgType() != MsgType.HASFINISHINGLINEBEENCROSSED);

        com.close();
        return hasFinished;
    }



    @Override
    public int getWinnerHorseId() {
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
        Message toSend = new Message(MsgType.GETWINNERHORSEID, null);

        com.writeObject(toSend);


        int winnerID;
        do{
            toSend = (Message) com.readObject();
            winnerID = (int)toSend.getArgs().get(0);
        }while(toSend.getMsgType() != MsgType.GETWINNERHORSEID);

        com.close();
        return winnerID;
    }
}
