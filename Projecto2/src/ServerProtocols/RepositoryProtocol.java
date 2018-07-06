package ServerProtocols;

import Monitors.repository.RepositoryInterface;
import comInf.messages.Message;
import comInf.messages.MessageException;
import comInf.messages.MsgType;

import java.util.ArrayList;

/**
 * Defines the service to be provided - Repository
 */

public class RepositoryProtocol {

    private RepositoryInterface repositoryInterface;

    /**
     * Constructor
     * @param repositoryInterface
     */
    public RepositoryProtocol(RepositoryInterface repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
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
            case GETNHORSES:
                toSend = new ArrayList<>();
                toSend.add(0,repositoryInterface.getNrHorses());
                break;

            case GETNSPECTATORS:
                toSend = new ArrayList<>();
                 toSend.add(0,repositoryInterface.getNrSpectactors());

                 break;

            case GETNRACES:
                 toSend = new ArrayList<>();
                 toSend.add(0,repositoryInterface.getNrRaces());
                 break;

            case GETRACEDISTANCE:
                 toSend = new ArrayList<>();
                 toSend.add(0,repositoryInterface.getRaceDistance());
                 break;
            case SETHORSESTATE:
                if ((int)inMessage.getArgs().get(0) <0)
                {
                    throw new MessageException(" ID invalid!", inMessage);
                }
                else if ((int)inMessage.getArgs().get(1) < 0) {
                    throw new MessageException("State invalid!", inMessage);
                }
                repositoryInterface.setHorseState((int)inMessage.getArgs().get(0), (int) inMessage.getArgs().get(1));

                break;

            case SETBROKERSTATE:
                if ((int)inMessage.getArgs().get(0) <0)
                {
                    throw new MessageException(" ID invalid!", inMessage);
                }
                repositoryInterface.setBrokerState((int)inMessage.getArgs().get(0));

                break;

            case SETSPECTATORSTATE:
                if ((int)inMessage.getArgs().get(0) <0)
                {
                    throw new MessageException(" ID invalid!", inMessage);
                }
                else if ((int)inMessage.getArgs().get(1) < 0) {
                    throw new MessageException("State invalid!", inMessage);
                }
                repositoryInterface.setSpectatorState((int)inMessage.getArgs().get(0), (int)inMessage.getArgs().get(1));

                break;

            case SETWINNINGPROB:
                if ((int)inMessage.getArgs().get(0) <0)
                {
                    throw new MessageException(" ID invalid!", inMessage);
                }
                else if ((String)inMessage.getArgs().get(1) == null) {
                    throw new MessageException("Winning Prob. invalid!", inMessage);
                }
                repositoryInterface.setWinningProbability((int)inMessage.getArgs().get(0), (String)inMessage.getArgs().get(1));

                break;

            case SETNRACES:
                if ((int)inMessage.getArgs().get(0) <0)
                {
                    throw new MessageException(" ID invalid!", inMessage);
                }
                repositoryInterface.setNrRaces((int)inMessage.getArgs().get(0));

                break;

            case REPORTSTATUS:
                repositoryInterface.reportStatus();

                break;

            case REPORTINITIALSTATUS:
                repositoryInterface.reportInitialStatus();

                break;

            case SETHORSESTEPS:
                if ((int)inMessage.getArgs().get(0) <0)
                {
                    throw new MessageException(" ID invalid!", inMessage);
                }
                else if ((int)inMessage.getArgs().get(1) < 0) {
                    throw new MessageException("Agility invalid!", inMessage);
                }
                repositoryInterface.setHorseSteps((int)inMessage.getArgs().get(0),(int)inMessage.getArgs().get(1));

                break;

            case SETHORSEPOSITION:
                if ((int)inMessage.getArgs().get(0) <0)
                {
                    throw new MessageException(" ID invalid!", inMessage);
                }
                else if ((int)inMessage.getArgs().get(1) < 0) {
                    throw new MessageException("Position invalid!", inMessage);
                }
                repositoryInterface.setHorsePosition((int)inMessage.getArgs().get(0), (int)inMessage.getArgs().get(1));

                break;

            case SETAMMOUNTOFMONEY:
                if ((int)inMessage.getArgs().get(0) <0)
                {
                    throw new MessageException(" Nr of spectators invalid", inMessage);
                }
                else if ((int)inMessage.getArgs().get(1) < 0) {
                    throw new MessageException("Total ammount of money invalid!", inMessage);
                }
                repositoryInterface.setAmountOfMoney((int)inMessage.getArgs().get(0), (int)inMessage.getArgs().get(1));

                break;

            case SETBETSELECTIONHORSE:
                if ((int)inMessage.getArgs().get(0) <0)
                {
                    throw new MessageException(" Nr of Spectators invalid!", inMessage);
                }
                else if ((int)inMessage.getArgs().get(1) < 0) {
                    throw new MessageException("Bet selection horse invalid!", inMessage);
                }
                repositoryInterface.setBetSelectionHorse((int)inMessage.getArgs().get(0),(int)inMessage.getArgs().get(1));
                toSend = new ArrayList<>();
                toSend.add(new Message(MsgType.ACK));
                break;

            case SETBETAMMOUNT:
                if ((int)inMessage.getArgs().get(0) <0)
                {
                    throw new MessageException(" Nr of Spectators invalid!", inMessage);
                }
                else if ((int)inMessage.getArgs().get(1) < 0) {
                    throw new MessageException("Bet Ammount invalid!", inMessage);
                }
                repositoryInterface.setBetAmount((int)inMessage.getArgs().get(0), (int)inMessage.getArgs().get(1));

                break;

            case SETNRSPECTATORS:
                if ((int)inMessage.getArgs().get(0) <0)
                {
                    throw new MessageException(" Nr of spectators invalid!", inMessage);
                }
                repositoryInterface.setNrSpectactors((int)inMessage.getArgs().get(0));

                break;

            case SETNRHORSES:
                if ((int)inMessage.getArgs().get(0) <0)
                {
                    throw new MessageException(" Nr of Horses invalid!", inMessage);
                }
                repositoryInterface.setNrHorses((int)inMessage.getArgs().get(0));

                break;

            case GETBROKERSTATE:
                int brokerState = repositoryInterface.getBrokerState();
                toSend = new ArrayList<>();
                toSend.add(0,brokerState);

                break;

            case SETHORSEMAXDISTANCE:
                if ((int)inMessage.getArgs().get(0) <0)
                {
                    throw new MessageException(" ID invalid!", inMessage);
                }
                else if ((int)inMessage.getArgs().get(1) < 0) {
                    throw new MessageException("Max Distance invalid!", inMessage);
                }
                repositoryInterface.setHorseMaxDistance((int)inMessage.getArgs().get(0), (int)inMessage.getArgs().get(1));

                break;

            default:
                throw new MessageException("Invalid message!", inMessage);
        }

        return toSend;
    }
}
