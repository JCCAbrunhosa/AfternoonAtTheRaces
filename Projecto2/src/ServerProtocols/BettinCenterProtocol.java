package ServerProtocols;

import Monitors.bettingCenter.BettingCenterInterface;
import comInf.messages.Message;
import comInf.messages.MessageException;
import comInf.messages.MsgType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Defines the service to be provided - Betting Center
 */
public class BettinCenterProtocol {

    BettingCenterInterface bettingCenterInterface;

    /**
     * Constructor
     * @param bettingCenterInterface
     */
    public BettinCenterProtocol(BettingCenterInterface bettingCenterInterface){
        this.bettingCenterInterface=bettingCenterInterface;
    }

    /**
     * Method of validation, processing and response of received messages
     * @param msg
     * @return Megessa
     * @throws MessageException
     */
    public ArrayList<Object> processInput(Message msg)throws MessageException{

        MsgType msgType = msg.getMsgType();
        int horseID ;
        int specID ;
        ArrayList<Object> toSend=null;

        switch(msgType){
            case PLACEABET:
                if ((int)msg.getArgs().get(0) < 0)
                {
                    throw new MessageException(" Spectator ID invalid!", msg);
                }else if((double)msg.getArgs().get(1) < 0){
                    throw new MessageException(" Bet value invalid!", msg);
                }else if((int)msg.getArgs().get(2) < 0){
                    throw new MessageException(" Horse ID invalid!", msg);
                }else if((double)msg.getArgs().get(3) < 0){
                    throw new MessageException(" Possible gains invalid!", msg);
                }
                specID = (int) msg.getArgs().get(0);
                double betValue = (double) msg.getArgs().get(1);
                horseID = (int) msg.getArgs().get(2);
                double possibleGain = (double) msg.getArgs().get(3);
                bettingCenterInterface.placeABet(specID,betValue,horseID,possibleGain);
                break;

            case ACCEPTTHEBETS:
                bettingCenterInterface.acceptTheBets();
                break;

            case GOCOLLECTTHEGAINS:
                if ((int)msg.getArgs().get(0) < 0)
                {
                    throw new MessageException(" Spectator ID invalid!", msg);
                }
                specID=(int) msg.getArgs().get(0);
                toSend = new ArrayList<>();
                toSend.add(0,bettingCenterInterface.goCollectTheGains(specID));
                break;

            case HONOURTHEBETS:
                if ((ArrayList<Integer>)msg.getArgs().get(0)==null)
                {
                    throw new MessageException(" Winner list invalid!", msg);
                }
                ArrayList<Integer> winnerList = (ArrayList<Integer>) msg.getArgs().get(0);
                bettingCenterInterface.honourTheBets(winnerList);
                break;

            case ARETHEREANYWINNERS:
                if ((int)msg.getArgs().get(0) < 0)
                {
                    throw new MessageException(" Winner Horse ID invalid!", msg);
                }
                int winnerHorse = (int) msg.getArgs().get(0);
                HashMap<Integer,Integer> winners = bettingCenterInterface.areThereAnyWinners(winnerHorse);
                toSend = new ArrayList<>();
                toSend.add(0,winners);

                break;

            case ARESPECSREADYTOBET:
                bettingCenterInterface.areSpectatorsReadyToBet();
                break;

            default:
                throw new MessageException("Invalid message!", msg);
        }
        return toSend;
    }
}
