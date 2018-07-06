package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Betting center interface
 */

public interface BettingCenterInterface extends Remote {
    /**
     * Method that accepts the bets, waking up spectators one by one
     *
     * Used by: Broker
     */
    void acceptTheBets() throws RemoteException;

    /**
     * Method that places a bet, adding that bet to the bets list
     *
     * Used by: Spectator
     *
     * @param specId
     * @param betValue
     * @param horseId
     * @param possibleGain
     */
    void placeABet(int specId, double betValue, int horseId, double possibleGain) throws RemoteException;

    /**
     * Method that returns the winning money if the spectator is on the winners list
     *
     * Used by: Spectator
     *
     * @param specId
     * @return gains
     */
    double goCollectTheGains(int specId) throws RemoteException;

    /**
     * Method that gives the winners their prize
     *
     * Used by: Broker
     *
     * @param winnersList
     */
    void honourTheBets(ArrayList<Integer> winnersList) throws RemoteException;

    /**
     * Method that returns the complete bet list to the Broker to see who has won or not
     *
     * Used by: Broker
     * @return winners
     */
    HashMap areThereAnyWinners(int winnerHorse) throws RemoteException;

    /**
     * Method that certifies if all spectators are ready to bet
     *
     * Used by: Broker
     */
    void areSpectatorsReadyToBet() throws RemoteException;

    /**
     * Método para "fechar" a classe - Apenas toma
     */
    void close() throws RemoteException ;

}
