package Monitors.repository;

import comInf.ClientCom;
import comInf.messages.Message;
import comInf.messages.MsgType;

import java.util.ArrayList;

/**
 * Class for sending messages between the betting center and the server
 */

public class ClientRepository implements RepositoryInterface{

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
     * Constructor that will creates the remote repository
     * @param hostName
     * @param portNumber
     */
    public ClientRepository(String hostName, int portNumber){
        this.hostName=hostName;
        this.portNumber=portNumber;
    }


    @Override
    public void reportInitialStatus() {
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
        toSend = new Message (MsgType.REPORTINITIALSTATUS, null);

        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.REPORTINITIALSTATUS);

        com.close();
    }

    @Override
    public void reportStatus() {
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
        toSend = new Message (MsgType.REPORTSTATUS, null);

        com.writeObject(toSend);

        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.REPORTSTATUS);

        com.close();
    }

    @Override
    public int getBrokerState() {
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
        //write message and waits for the reply
        toSend = new Message (MsgType.GETBROKERSTATE, null);
        com.writeObject(toSend);
        int brokerState;

        do{
            toSend = (Message) com.readObject();
            brokerState = (int) toSend.getArgs().get(0);
        }while(toSend.getMsgType() != MsgType.GETBROKERSTATE);
        com.close();
        return brokerState;
    }

    @Override
    public void setBrokerState(int brokerState) {
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
        //write message and waits for the reply
        toSend = new Message (MsgType.SETBROKERSTATE, null);
        ArrayList<Object> args = new ArrayList<>();
        args.add(0,brokerState);
        toSend.setArgs(args);

        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.SETBROKERSTATE);
        com.close();
    }

    @Override
    public void setSpectatorState(int id, int spectatorState) {
        ClientCom com = new ClientCom(hostName,portNumber);
        while(!com.open()){
            //
            //write message and waits for the reply
            try {
                Thread.currentThread().sleep((long) 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //com.open();
        toSend = new Message (MsgType.SETSPECTATORSTATE, null);
        ArrayList<Object> args = new ArrayList<>();
        args.add(0, id);
        args.add(1, spectatorState);

        toSend.setArgs(args);
        com.writeObject(toSend);

        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.SETSPECTATORSTATE);

        com.close();

    }

    @Override
    public void setHorseState(int id, int horseState) {
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
        //write message and waits for the reply
        toSend = new Message (MsgType.SETHORSESTATE, null);
        ArrayList<Object> args = new ArrayList<>();
        args.add(0,id);
        args.add(1, horseState);

        toSend.setArgs(args);
        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.SETHORSESTATE);

        com.close();
    }

    @Override
    public void setHorseMaxDistance(int id, int horseMaxDistance) {
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
        toSend = new Message (MsgType.SETHORSEMAXDISTANCE, null);
        ArrayList<Object> args = new ArrayList<>();
        args.add(0,id);
        args.add(1,horseMaxDistance);

        toSend.setArgs(args);
        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.SETHORSEMAXDISTANCE);
        com.close();
    }

    @Override
    public int getNrHorses() {
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
        //write message and waits for the reply
        toSend = new Message(MsgType.GETNHORSES, null);
        com.writeObject(toSend);


        int numHorses;
        do{
            toSend = (Message) com.readObject();
            numHorses = (int) toSend.getArgs().get(0);
        }while(toSend.getMsgType() != MsgType.GETNHORSES);

        com.close();
        return numHorses;
    }

    @Override
    public void setNrHorses(int nrHorses) {
        ClientCom com = new ClientCom(hostName,portNumber);
        com.open();
        ArrayList<Object> args = new ArrayList<>();
        args.add(0,nrHorses);

        toSend.setMsgType(MsgType.SETNRHORSES);
        toSend.setArgs(args);
        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.GETBROKERSTATE);
        com.close();
    }

    @Override
    public int getNrSpectactors() {
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
        //write message and waits for the reply
        toSend = new Message (MsgType.GETNSPECTATORS, null);

        com.writeObject(toSend);

        int numSpecs;
        do{
            toSend = (Message) com.readObject();
            numSpecs = (int) toSend.getArgs().get(0);
        }while(toSend.getMsgType() != MsgType.GETNSPECTATORS);

        com.close();
        return numSpecs;
    }

    @Override
    public void setNrSpectactors(int nrSpectactors) {
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
        toSend = new Message (MsgType.SETNRSPECTATORS, null);
        ArrayList<Object> args = new ArrayList<>();
        args.add(0,nrSpectactors);
        toSend.setArgs(args);

        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.SETNRSPECTATORS);

        com.close();
    }

    @Override
    public int getNrRaces() {
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
        //write message and waits for the reply
        toSend = new Message (MsgType.GETNRACES, null);

        com.writeObject(toSend);

        int numRaces ;

        do{
            toSend = (Message) com.readObject();
            numRaces = (int) toSend.getArgs().get(0);
        }while(toSend.getMsgType() != MsgType.GETNRACES);

        com.close();
        return numRaces;
    }

    @Override
    public void setNrRaces(int nrRaces) {
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
        toSend = new Message (MsgType.SETNRACES, null);
        ArrayList<Object> args = new ArrayList<>();
        args.add(0,nrRaces);

        toSend.setArgs(args);

        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.SETNRACES);

        com.close();

    }

    @Override
    public int getRaceDistance() {
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

        toSend = new Message (MsgType.GETRACEDISTANCE, null);

        com.writeObject(toSend);

        int raceDist;

        do{
            toSend = (Message) com.readObject();
            raceDist = (int) toSend.getArgs().get(0);
        }while(toSend.getMsgType() != MsgType.GETRACEDISTANCE);

        com.close();
        return raceDist;
    }

    @Override
    public void setAmountOfMoney(int nrSpectactors, int amountOfMoney) {
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
        toSend = new Message (MsgType.SETAMMOUNTOFMONEY, null);
        ArrayList<Object> args = new ArrayList<>();
        args.add(0,nrSpectactors);
        args.add(1,amountOfMoney);

        toSend.setArgs(args);

        //send value to server
        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.SETAMMOUNTOFMONEY);

        com.close();
    }

    @Override
    public void setBetSelectionHorse(int nrSpectactors, int betSelectionHorse) {
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

        toSend = new Message (MsgType.SETBETSELECTIONHORSE, null);

        ArrayList<Object> args = new ArrayList<>();
        args.add(0,nrSpectactors);
        args.add(1,betSelectionHorse);

        toSend.setArgs(args);

        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.SETBETSELECTIONHORSE);
        com.close();
    }

    @Override
    public void setBetAmount(int nrSpectactors, int betAmount) {
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

        toSend = new Message (MsgType.SETBETAMMOUNT, null);
        ArrayList<Object> args = new ArrayList<>();
        args.add(0,nrSpectactors);
        args.add(1,betAmount);

        toSend.setArgs(args);
        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.SETBETAMMOUNT);
        com.close();
    }

    @Override
    public void setWinningProbability(int nrHorses, String winningProbability) {
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
        toSend = new Message (MsgType.SETWINNINGPROB, null);
        ArrayList<Object> args = new ArrayList();
        args.add(0,nrHorses);
        args.add(1,winningProbability);
        toSend.setArgs(args);

        com.writeObject(toSend);

        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.SETWINNINGPROB);


        com.close();
    }

    @Override
    public void setHorseSteps(int id, int horseSteps) {
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
        toSend = new Message (MsgType.SETHORSESTEPS, null);
        ArrayList<Object> args = new ArrayList<>();
        args.add(0,id);
        args.add(1,horseSteps);
        toSend.setArgs(args);

        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.SETHORSESTEPS);

        com.close();
    }

    @Override
    public void setHorsePosition(int id, int horsePosition) {
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
        toSend = new Message (MsgType.SETHORSEPOSITION, null);

        ArrayList<Object> args = new ArrayList<>();
        args.add(0,id);
        args.add(1,horsePosition);

        toSend.setArgs(args);
        com.writeObject(toSend);
        do{
            toSend = (Message) com.readObject();
        }while(toSend.getMsgType() != MsgType.SETHORSEPOSITION);
        com.close();
    }
}
