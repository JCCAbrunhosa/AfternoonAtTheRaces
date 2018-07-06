package ServerProtocols;

import Monitors.racingTrack.RacingTrackInterface;
import comInf.messages.Message;
import comInf.messages.MessageException;
import comInf.messages.MsgType;

import java.util.ArrayList;

/**
 * Defines the service to be provided - Racing Track
 */
public class RacingTrackProtocol  {

    public RacingTrackInterface racingTrackInterface;

    /**
     * Constructor
     * @param racingTrackInterface
     */
    public RacingTrackProtocol(RacingTrackInterface racingTrackInterface) {
        this.racingTrackInterface=racingTrackInterface;
    }

    /**
     * Method of validation, processing and response of received messages
     * @param msg
     * @return
     * @throws MessageException
     */
    public ArrayList<Object> processInput(Message msg) throws MessageException{

        MsgType msgType = msg.getMsgType();
        int horseID;

        ArrayList<Object> toSend = null;

        switch (msgType){

            case PROCEEDTOSTARTLINE:
                if ((int)msg.getArgs().get(0) < 0)
                {
                    throw new MessageException("Horse ID invalid!", msg);
                }
                horseID =(int)msg.getArgs().get(0);
                racingTrackInterface.proceedToStartLine(horseID);
                break;

            case STARTRACE:
                racingTrackInterface.startRace();
                break;

            case MAKEAMOVE:
                if ((int)msg.getArgs().get(0) < 0)
                {
                    throw new MessageException("Horse ID invalid!", msg);
                }
                horseID =(int)msg.getArgs().get(0);
                int maxDistance = (int) msg.getArgs().get(1);
                racingTrackInterface.makeAMove(horseID,maxDistance);
                break;

            case HASFINISHINGLINEBEENCROSSED:
                if ((int)msg.getArgs().get(0) < 0)
                {
                    throw new MessageException("Horse ID invalid!", msg);
                }
                horseID =(int)msg.getArgs().get(0);
                boolean finishingLineCrossed = racingTrackInterface.hasFinishingLineBeenCrossed(horseID);
                toSend = new ArrayList<>();
                toSend.add(0,finishingLineCrossed);

                break;

            case GETWINNERHORSEID:
                int winnerHorseID= racingTrackInterface.getWinnerHorseId();
                toSend= new ArrayList<>();
                toSend.add(0,winnerHorseID);
                break;
            default:
                throw new MessageException("Invalid message!", msg);
        }
        return toSend;
    }
}
