package Monitors.paddock;

import java.util.HashMap;

/**
 * Interface Paddock
 */

public interface PaddockInterface {
    /**
     * Go check the horses
     * @param specId Spectator ID
     * @return winningChance - Winning chance of the Horse that the spectator is betting on
     *
     *
     */
    int goCheckHorses(int specId);

    /**
     * Proceed to Paddock
     *
     * @param horseId Horse ID
     * @param horseWinningChance Horse winning chance
     */
    void proceedToPaddock(int horseId, double horseWinningChance);

    /**
     * Get the horses winning chances
     *
     * @return winningChances - List containing the winning chances for all horses
     *
     */
    HashMap getWinningChances();
}
