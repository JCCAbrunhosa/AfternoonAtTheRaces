package Monitors;

public interface RacingTrackInterface {
    void proceedToStartLine(int horseId);

    void startRace();

    void makeAMove(int horseId, int maxDistance);

    boolean hasFinishingLineBeenCrossed(int horseId);

    int getWinnerHorseId();
}
