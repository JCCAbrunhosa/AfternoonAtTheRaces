package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface racing track
 */
public interface RacingTrackInterface extends Remote {
    /**
     * Method used by the horse to proceed to start line
     *
     * Used by: Horse
     *
     * @param horseId
     */
    void proceedToStartLine(int horseId) throws RemoteException;

    /**
     * Starts the Race
     *
     * Used by: Broker
     */
    void startRace() throws RemoteException;

    /**
     * Makes a move in the racing track
     *
     * Used by: Horse
     *
     * @param horseId
     * @param maxDistance
     */
    void makeAMove(int horseId, int maxDistance) throws RemoteException;

    /**
     * Checks if the finish line has been crossed
     *
     * Used by: Horse
     *
     * @param horseId
     * @return boolean
     */
    boolean hasFinishingLineBeenCrossed(int horseId) throws RemoteException;

    /**
     * Returns the Winner horse id
     *
     * Used by: Broker
     *
     * @return winnerHorse
     */
    int getWinnerHorseId() throws RemoteException;

    /**
     * Método para "fechar" a classe - Apenas toma valor na classe remota.
     */
    void close() throws RemoteException ;

}
