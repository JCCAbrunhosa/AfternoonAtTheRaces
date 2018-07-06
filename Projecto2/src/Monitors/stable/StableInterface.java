package Monitors.stable;

/**
 * Interface stable
 */

public interface StableInterface {
    /**
     * Summon all horses to paddock
     */
    void summonHorsesToPaddock();

    /**
     * Proceed to the stable
     *
     * @param horseId Horse ID
     */
    void proceedToStable(int horseId);
}
