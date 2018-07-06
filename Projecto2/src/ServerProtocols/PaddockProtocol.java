package ServerProtocols;

import Monitors.paddock.PaddockInterface;
import comInf.messages.Message;
import comInf.messages.MessageException;
import comInf.messages.MsgType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Defines the service to be provided - Paddock
 */
public class PaddockProtocol {

    private PaddockInterface paddockInterface;

    /**
     * Constructor
     * @param paddockInterface
     */
    public PaddockProtocol(PaddockInterface paddockInterface) {
        this.paddockInterface = paddockInterface;
    }

    /**
     * Method of validation, processing and response of received messages
     *
     * @param inMessage
     * @return Message
     * @throws MessageException
     */
    public ArrayList<Object> processInput(Message inMessage) throws MessageException {

        MsgType msgType = inMessage.getMsgType();
        ArrayList<Object> toSend = null;

        switch (msgType){
            case GETWINNINGCHANCES:
                HashMap<Integer, Integer> winningChances = paddockInterface.getWinningChances();
                toSend = new ArrayList<>();
                toSend.add(0,winningChances);

                break;

            case PROCEEDTOPADDOCK:
                if ((int)inMessage.getArgs().get(0) < 0)
                {
                    throw new MessageException(" ID invalid!", inMessage);
                }
                else if ((double) inMessage.getArgs().get(1) < 0) {
                    throw new MessageException("Horse winning chance invalid!", inMessage);
                }
                int horseID = (int) inMessage.getArgs().get(0);
                double winningChance = (double) inMessage.getArgs().get(1);
                paddockInterface.proceedToPaddock(horseID, winningChance);

                break;

            case GOCHECKHORSES:
                if ((int)inMessage.getArgs().get(0) <0)
                {
                    throw new MessageException(" ID invalid!", inMessage);
                }
                int specID = (int) inMessage.getArgs().get(0);
                int horseWinningChance = paddockInterface.goCheckHorses(specID);
                toSend = new ArrayList<>();
                toSend.add(0,horseWinningChance);

                break;

            default:
                throw new MessageException("Invalid message!", inMessage);
        }

        return toSend;
    }
}
