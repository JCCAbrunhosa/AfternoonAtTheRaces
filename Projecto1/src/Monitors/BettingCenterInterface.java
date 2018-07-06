package Monitors;

import java.util.ArrayList;
import java.util.HashMap;

public interface BettingCenterInterface {
    void acceptTheBets();

    void placeABet(int specId, double betValue, int horseId, double possibleGain);

    double goCollectTheGains(int specId);

    void honourTheBets(ArrayList<Integer> winnersList);

    HashMap areThereAnyWinners(int winnerHorse);

    void areSpectatorsReadyToBet();
}
