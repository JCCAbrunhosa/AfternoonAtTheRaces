package Monitors;

public interface RepositoryInterface {
    void reportInitialStatus();

    void reportStatus();

    int getBrokerState();

    void setBrokerState(int brokerState);

    void setSpectatorState(int id, int spectatorState);

    void setHorseState(int id, int horseState);

    void setHorseMaxDistance(int id, int horseMaxDistance);

    int getNrHorses();

    void setNrHorses(int nrHorses);

    int getNrSpectactors();

    void setNrSpectactors(int nrSpectactors);

    int getNrRaces();

    void setNrRaces(int nrRaces);

    int getRaceDistance();

    void setAmountOfMoney(int nrSpectactors, int amountOfMoney);

    void setBetSelectionHorse(int nrSpectactors, int betSelectionHorse);

    void setBetAmount(int nrSpectactors, int betAmount);

    void setWinningProbability(int nrHorses, String winningProbability);

    void setHorseSteps(int id, int horseSteps);

    void setHorsePosition(int id, int horsePosition);
}
