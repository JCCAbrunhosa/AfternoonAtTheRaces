package Monitors;

import java.util.HashMap;

public interface PaddockInterface {
    int goCheckHorses(int specId);

    void proceedToPaddock(int horseId, double horseWinningChance);

    HashMap getWinningChances();
}
