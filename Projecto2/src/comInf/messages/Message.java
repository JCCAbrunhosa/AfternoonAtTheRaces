package comInf.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Esta class define como as mensagens são trocadas entre os clientes e o servidor
 *
 */

public class Message extends ArrayList<Object> implements Serializable {

    /**
     *  Serialization key
     *    @serialField serialVersionUID
     */
    private static final long serialVersionUID = 1002L;
    /**
     * Type of mensage to send.
     * @serialField msgType
     */
    private MsgType msgType;
    private int value;


    public Message(MsgType type, int value) {
        msgType = type;
        this.value = value;
    }

    /**
     * Parameters to pass according to the message type
     * @serialField args
     */
    private ArrayList<Object> args;

    /**
     * Constructor with one message type
     * @param type
     */
    public Message(MsgType type) {
        msgType = type;
    }

    /**
     * Constructor with a message type and an ArrayList
     * @param type
     * @param args
     */
    public Message(MsgType type, ArrayList<Object> args) {
        msgType = type;
        this.args = args;
    }

    /**
     * Method to update message type
     * @param msgType Updates message type
     */
    public void setMsgType(MsgType msgType){
        this.msgType=msgType;
    }

    /**
     * Method that updates a set of parameters
     * @param args Parameters to update
     */
    public void setArgs(ArrayList<Object> args){
        this.args=args;
    }

    /**
     * Method that returns an array with
     * the required parameters of the message type
     *
     * @return ArrayList
     */
    public ArrayList<Object> getArgs(){
        return this.args;
    }

    /**
     * Method that returns an integer
     * @return value
     */
    public int getMsg(){
        return this.value;
    }

    /**
     * Method to return message type
     *
     * @return Message type
     */
    public MsgType getMsgType()
    {
        return msgType;
    }



}
