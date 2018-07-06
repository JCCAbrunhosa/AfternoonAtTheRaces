package Monitors.racingTrack;

/**
 * Interface racing track
 */
public interface RacingTrackInterface {
    /**
     * Method used by the horse to proceed to start line
     *
     * Used by: Horse
     *
     * @param horseId
     */
    void proceedToStartLine(int horseId);

    /**
     * Starts the Race
     *
     * Used by: Broker
     */
    void startRace();

    /**
     * Makes a move in the racing track
     *
     * Used by: Horse
     *
     * @param horseId
     * @param maxDistance
     */
    void makeAMove(int horseId, int maxDistance);

    /**
     * Checks if the finish line has been crossed
     *
     * Used by: Horse
     *
     * @param horseId
     * @return boolean
     */
    boolean hasFinishingLineBeenCrossed(int horseId);

    /**
     * Returns the Winner horse id
     *
     * Used by: Broker
     *
     * @return winnerHorse
     */
    int getWinnerHorseId();
}
