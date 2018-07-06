package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Repository Interface
 */
public interface RepositoryInterface extends Remote {

    /**
     * Report the initial status
     */
    void reportInitialStatus() throws RemoteException;

    /**
     * Report actual status
     */
    void reportStatus() throws RemoteException;

    /**
     * Get broker state
     *
     * @return brokerState
     */
    int getBrokerState() throws RemoteException;

    /**
     * Set broker state
     *
     * @param brokerState
     */
    void setBrokerState(int brokerState) throws RemoteException;

    /**
     * Set Spectator State
     *
     * @param id
     * @param spectatorState
     */
    void setSpectatorState(int id, int spectatorState) throws RemoteException;

    /**
     * Set Horse state
     *
     * @param id
     * @param horseState
     */
    void setHorseState(int id, int horseState) throws RemoteException;

    /**
     * Set Horse Max Distance
     *
     * @param id
     * @param horseMaxDistance
     */
    void setHorseMaxDistance(int id, int horseMaxDistance) throws RemoteException;

    /**
     * Get number of Horses
     *
     * @return nrHorses
     */
    int getNrHorses() throws RemoteException;

    /**
     * Set number of Horses
     *
     * @param nrHorses
     */
    void setNrHorses(int nrHorses) throws RemoteException;

    /**
     * Get the number of Spectators
     *
     * @return nrSpectators
     */
    int getNrSpectactors() throws RemoteException;

    /**
     * Set the number of Spectators
     *
     * @param nrSpectactors
     */
    void setNrSpectactors(int nrSpectactors) throws RemoteException;

    /**
     * Get the number of Races
     *
     * @return nrRaces
     */
    int getNrRaces() throws RemoteException;

    /**
     * Set the number of Races
     *
     * @param nrRaces
     */
    void setNrRaces(int nrRaces) throws RemoteException;

    /**
     * Get the race distance
     *
     * @return raceDistance
     */
    int getRaceDistance() throws RemoteException;

    /**
     * Set the Ammount of money for spectator
     *
     * @param nrSpectactors
     * @param amountOfMoney
     */
    void setAmountOfMoney(int nrSpectactors, int amountOfMoney) throws RemoteException;

    /**
     * Set the Bet selected horse
     *
     * @param nrSpectactors
     * @param betSelectionHorse
     */
    void setBetSelectionHorse(int nrSpectactors, int betSelectionHorse) throws RemoteException;

    /**
     * Set the Bet ammount
     *
     * @param nrSpectactors
     * @param betAmount
     */
    void setBetAmount(int nrSpectactors, int betAmount) throws RemoteException;

    /**
     * Set the Winning prob
     *
     * @param nrHorses
     * @param winningProbability
     */
    void setWinningProbability(int nrHorses, String winningProbability) throws RemoteException;

    /**
     * Set the Horse move
     *
     * @param id
     * @param horseSteps
     */
    void setHorseSteps(int id, int horseSteps) throws RemoteException;

    /**
     * Set the horse Position
     *
     * @param id
     * @param horsePosition
     */
    void setHorsePosition(int id, int horsePosition) throws RemoteException;

    /**
     * Método para "fechar" a classe - Apenas toma
     */
    void close() throws RemoteException ;
}
