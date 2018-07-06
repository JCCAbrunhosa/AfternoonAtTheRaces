package Monitors;

import java.util.ArrayList;
import java.util.HashMap;

public interface ControlCenterInterface {
    void waitForNextRace(int specId);

    void goWatchTheRace(int specId);

    ArrayList reportResults(HashMap<Integer, Integer> betList, int winnerHorse);

    void allHorsesOnPaddock();

    boolean haveIWon(int specId);

    void entertainTheGuests();

    void relaxABit(int specId, double finalValue);

    void allSpectatorsReady();
}
