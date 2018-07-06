package ServerProtocols;

import Monitors.controlCenter.ControlCenterInterface;
import comInf.messages.Message;
import comInf.messages.MessageException;
import comInf.messages.MsgType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Defines the service to be provided - Control center
 */

public class ControlCenterProtocol {

    private ControlCenterInterface controlCenterInterface;

    /**
     * Constructor
     * @param controlCenterInterface
     */
    public ControlCenterProtocol(ControlCenterInterface controlCenterInterface){
        this.controlCenterInterface=controlCenterInterface;
    }

    /**
     * Method of validation, processing and response of received messages
     * @param inMsg
     * @return
     * @throws MessageException
     */
    public ArrayList<Object> processInput(Message inMsg) throws MessageException{

        ArrayList<Object> toSend=null;
        int specID;
        MsgType msgType = inMsg.getMsgType();

        switch (msgType){

            case WAITFORNEXTRACE:
                if ((int)inMsg.getArgs().get(0) < 0){
                    throw new MessageException(" ID invalid!", inMsg);
                }
                specID = (int)inMsg.getArgs().get(0);
                controlCenterInterface.waitForNextRace(specID);
                break;

            case GOWATCHTHERACE:
                if ((int)inMsg.getArgs().get(0) < 0){
                    throw new MessageException(" ID invalid!", inMsg);
                }
                specID = (int)inMsg.getArgs().get(0);
                controlCenterInterface.goWatchTheRace(specID);
                break;

            case ALLHORSESONPADDOCK:
                controlCenterInterface.allHorsesOnPaddock();
                break;

            case ALLSPECTATORSREADY:
                controlCenterInterface.allSpectatorsReady();
                break;

            case HAVEIWON:
                if ((int)inMsg.getArgs().get(0) < 0){
                    throw new MessageException(" ID invalid!", inMsg);
                }
                specID = (int)inMsg.getArgs().get(0);
                boolean haveIWon = controlCenterInterface.haveIWon(specID);
                toSend = new ArrayList<>();
                toSend.add(haveIWon);
                break;

            case ENTERTAINTHEGUESTS:
                controlCenterInterface.entertainTheGuests();
                break;

            case RELAXABIT:
                if ((int)inMsg.getArgs().get(0) < 0){
                    throw new MessageException(" ID invalid!", inMsg);
                }
                specID = (int)inMsg.getArgs().get(0);
                double finalValue = (double) inMsg.getArgs().get(1);
                controlCenterInterface.relaxABit(specID,finalValue);
                break;

            case REPORTRESULTS:
                if ((HashMap<Integer, Integer>)inMsg.getArgs().get(0) == null){
                    throw new MessageException(" Bet List invalid!", inMsg);
                }else if((int)inMsg.getArgs().get(1) < 0){
                    throw new MessageException(" Winner horse ID invalid!", inMsg);
                }
                HashMap<Integer, Integer> betList = (HashMap<Integer, Integer>) inMsg.getArgs().get(0);
                int winnerHorse = (int) inMsg.getArgs().get(1);
                ArrayList<Object> results = controlCenterInterface.reportResults(betList,winnerHorse);
                toSend = new ArrayList<>();
                toSend.add(results);
                break;
            default:
                throw new MessageException("Invalid message!", inMsg);

        }

        return toSend;
    }
}
