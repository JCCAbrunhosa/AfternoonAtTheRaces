package Monitors.controlCenter;

import java.util.ArrayList;
import java.util.HashMap;

public interface ControlCenterInterface {
    /**
     * Waiting for next race
     *
     * @param specId
     *
     * Used by: Spectator
     */
    void waitForNextRace(int specId);

    /**
     * Going to watch the race
     *
     * @param specId
     *
     * Used by: Spectator
     */
    void goWatchTheRace(int specId);

    /**
     * Reports the results for each race
     *
     * @param betList
     * @param winnerHorse
     * @return ArrayList containing the winners
     *
     * Used by: Broker
     */
    ArrayList reportResults(HashMap<Integer, Integer> betList, int winnerHorse);

    /**
     * Checks if all the horses are on Paddock
     *
     * Used by: Broker
     */
    void allHorsesOnPaddock();

    /**
     * Sees if he has won the bet
     *
     * @param specId
     * @return Boolean showing if he won or not
     *
     * Used by: Spectator
     */
    boolean haveIWon(int specId);

    /**
     * Entertain the guests
     */
    void entertainTheGuests();

    /**
     * Relax a bit after the race
     * @param specId
     * @param finalValue
     */
    void relaxABit(int specId, double finalValue);

    /**
     * Spectators are ready
     */
    void allSpectatorsReady();
}
