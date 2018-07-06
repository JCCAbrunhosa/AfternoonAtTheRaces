package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface ControlCenterInterface extends Remote {
    /**
     * Waiting for next race
     *
     * @param specId
     *
     * Used by: Spectator
     */
    void waitForNextRace(int specId) throws RemoteException;

    /**
     * Going to watch the race
     *
     * @param specId
     *
     * Used by: Spectator
     */
    void goWatchTheRace(int specId) throws RemoteException;

    /**
     * Reports the results for each race
     *
     * @param betList
     * @param winnerHorse
     * @return ArrayList containing the winners
     *
     * Used by: Broker
     */
    ArrayList reportResults(HashMap<Integer, Integer> betList, int winnerHorse) throws RemoteException;

    /**
     * Checks if all the horses are on Paddock
     *
     * Used by: Broker
     */
    void allHorsesOnPaddock() throws RemoteException;

    /**
     * Sees if he has won the bet
     *
     * @param specId
     * @return Boolean showing if he won or not
     *
     * Used by: Spectator
     */
    boolean haveIWon(int specId) throws RemoteException;

    /**
     * Entertain the guests
     */
    void entertainTheGuests() throws RemoteException;

    /**
     * Relax a bit after the race
     * @param specId
     * @param finalValue
     */
    void relaxABit(int specId, double finalValue) throws RemoteException;

    /**
     * Spectators are ready
     */
    void allSpectatorsReady() throws RemoteException;

    /**
     * Método para "fechar" a classe - Apenas toma
     */
    void close() throws RemoteException ;
}
