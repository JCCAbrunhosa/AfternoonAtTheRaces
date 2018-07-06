package Monitors.repository;

/**
 * Repository Interface
 */
public interface RepositoryInterface {

    /**
     * Report the initial status
     */
    void reportInitialStatus();

    /**
     * Report actual status
     */
    void reportStatus();

    /**
     * Get broker state
     *
     * @return brokerState
     */
    int getBrokerState();

    /**
     * Set broker state
     *
     * @param brokerState
     */
    void setBrokerState(int brokerState);

    /**
     * Set Spectator State
     *
     * @param id
     * @param spectatorState
     */
    void setSpectatorState(int id, int spectatorState);

    /**
     * Set Horse state
     *
     * @param id
     * @param horseState
     */
    void setHorseState(int id, int horseState);

    /**
     * Set Horse Max Distance
     *
     * @param id
     * @param horseMaxDistance
     */
    void setHorseMaxDistance(int id, int horseMaxDistance);

    /**
     * Get number of Horses
     *
     * @return nrHorses
     */
    int getNrHorses();

    /**
     * Set number of Horses
     *
     * @param nrHorses
     */
    void setNrHorses(int nrHorses);

    /**
     * Get the number of Spectators
     *
     * @return nrSpectators
     */
    int getNrSpectactors();

    /**
     * Set the number of Spectators
     *
     * @param nrSpectactors
     */
    void setNrSpectactors(int nrSpectactors);

    /**
     * Get the number of Races
     *
     * @return nrRaces
     */
    int getNrRaces();

    /**
     * Set the number of Races
     *
     * @param nrRaces
     */
    void setNrRaces(int nrRaces);

    /**
     * Get the race distance
     *
     * @return raceDistance
     */
    int getRaceDistance();

    /**
     * Set the Ammount of money for spectator
     *
     * @param nrSpectactors
     * @param amountOfMoney
     */
    void setAmountOfMoney(int nrSpectactors, int amountOfMoney);

    /**
     * Set the Bet selected horse
     *
     * @param nrSpectactors
     * @param betSelectionHorse
     */
    void setBetSelectionHorse(int nrSpectactors, int betSelectionHorse);

    /**
     * Set the Bet ammount
     *
     * @param nrSpectactors
     * @param betAmount
     */
    void setBetAmount(int nrSpectactors, int betAmount);

    /**
     * Set the Winning prob
     *
     * @param nrHorses
     * @param winningProbability
     */
    void setWinningProbability(int nrHorses, String winningProbability);

    /**
     * Set the Horse move
     *
     * @param id
     * @param horseSteps
     */
    void setHorseSteps(int id, int horseSteps);

    /**
     * Set the horse Position
     *
     * @param id
     * @param horsePosition
     */
    void setHorsePosition(int id, int horsePosition);
}
