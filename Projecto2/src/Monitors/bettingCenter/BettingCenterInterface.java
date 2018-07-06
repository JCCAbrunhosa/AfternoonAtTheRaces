package Monitors.bettingCenter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Betting center interface
 */

public interface BettingCenterInterface {
    /**
     * Method that accepts the bets, waking up spectators one by one
     *
     * Used by: Broker
     */
    void acceptTheBets();

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
    void placeABet(int specId, double betValue, int horseId, double possibleGain);

    /**
     * Method that returns the winning money if the spectator is on the winners list
     *
     * Used by: Spectator
     *
     * @param specId
     * @return
     */
    double goCollectTheGains(int specId);

    /**
     * Method that gives the winners their prize
     *
     * Used by: Broker
     *
     * @param winnersList
     */
    void honourTheBets(ArrayList<Integer> winnersList);

    /**
     * Method that returns the complete bet list to the Broker to see who has won or not
     *
     * Used by: Broker
     * @return
     */
    HashMap areThereAnyWinners(int winnerHorse);

    /**
     * Method that certifies if all spectators are ready to bet
     *
     * Used by: Broker
     */
    void areSpectatorsReadyToBet();
}
