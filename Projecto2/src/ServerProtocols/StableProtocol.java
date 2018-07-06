package ServerProtocols;

import Monitors.stable.StableInterface;
import comInf.messages.Message;
import comInf.messages.MessageException;
import comInf.messages.MsgType;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Defines the service to be provided - Stable
 */

public class StableProtocol {

    private StableInterface stableInterface;

    /**
     * Constructor
     * @param stableInterface
     */
    public StableProtocol(StableInterface stableInterface) {
        this.stableInterface = stableInterface;
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
        ArrayList<Object> toSend=null;

        switch (msgType){
            case SUMMONHORSESTOPADDOCK:
                stableInterface.summonHorsesToPaddock();
                break;

            case PROCEEDTOSTABLE:
                if (inMessage.getMsg() <0)
                {
                    throw new MessageException(" ID invalid!", inMessage);
                }
                int horseID = (int) inMessage.getArgs().get(0);
                stableInterface.proceedToStable(horseID);
                toSend = new ArrayList<>();
                toSend.add(new Message(MsgType.ACK));
                break;

            default:
                throw new MessageException("Invalid message!", inMessage);
        }

        return toSend;
    }
}
