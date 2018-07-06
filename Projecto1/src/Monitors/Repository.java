package Monitors;

import Extras.Constants;
import genclass.GenericIO;
import genclass.TextFile;

import java.util.Objects;
/**
 *
 */
public class Repository implements RepositoryInterface {

    /**
     * Name of LogFile
     * @serialField logName
     */
    private String logName;

    /**
     * TextFile object to append text
     * @serialField log
     */
    private TextFile log;
    /**
     *  Broker State
     *
     * @serialField brokerState
     */
    private int brokerState;

    /**
     * Spectators state
     *
     * @serialField spectatorState
     */
    private int []spectatorState;

    /**
     * Horses state
     *
     * @serialField horseState
     */
    private int []horseState;

    /**
     * Max distance for each horse
     *
     * @serialField horseMaxDistance
     */
    private int []horseMaxDistance;

    /**
     * Number of horses
     *
     * @serialField nrHorses
     */
    private int nrHorses;

    /**
     * Number of spectators
     *
     *@serialField nrSpectators
     */
    private int nrSpectactors;

    /**
     * Number of Races
     *
     * @serialField nrRaces
     */
    private int nrRaces;

    /**
     * Race distance
     *
     * @serialField raceDistance
     */
    private int raceDistance;

    /**
     * The ammount of money of each spectator
     *
     * @serialField ammountOfMoney
     */
    private int []amountOfMoney;

    /**
     * Horse selected for bet
     *
     * @serialField betSelectionHorse
     */
    private int []betSelectionHorse;

    /**
     * Ammount of the bet
     *
     * @serialField betAmount
     */
    private int []betAmount;

    /**
     * Chances of a horse winning
     *
     * @serialField winningProbability
     */
    private String []winningProbability;

    /**
     * Horse move
     *
     * @serialField horseSteps
     */
    private int []horseSteps;

    /**
     * Horse position
     *
     * @serialField horsePosition
     */
    private int []horsePosition;

    /**
     * Repository constructor
     *
     * @param nrHorses Number of Horses
     * @param nrSpectators Number of Spectators
     * @param nrRaces Number of Races
     * @param raceDistance Race distance
     * @param logName Log file Name
     */
    public Repository(int nrHorses, int nrSpectators, int nrRaces, int raceDistance, String logName){
        this.nrHorses=nrHorses;
        this.nrSpectactors=nrSpectators;
        this.nrRaces=nrRaces;
        this.raceDistance=raceDistance;
        spectatorState = new int [5];
        horseState = new int [5];
        horseMaxDistance = new int[10];
        amountOfMoney = new int [5];
        betSelectionHorse = new int [5];
        betAmount = new int [5];
        winningProbability = new String[5];
        horsePosition = new int [5];
        horseSteps = new int [5];
        log = new TextFile();

        this.logName = "Races.log";
        if (!Objects.equals(logName, ""))
            this.logName = logName;

        for (int i=0; i<5; i++){
            spectatorState[i] = -1;
            horseState[i] = -1;
            amountOfMoney[i]=-1;
            betSelectionHorse[i]=-1;
            betAmount[i]=-1;
            winningProbability[i]=null;
            horsePosition[i]=-1;
            horseSteps[i]=-1;
        }

        for (int i=0; i<10; i++){
            horseMaxDistance[i] = -1;
        }

        reportInitialStatus();
    }

    /**
     * Report the initial status
     *
     *
     */
    @Override
    public synchronized void reportInitialStatus() {

        if (!log.openForWriting(".",logName)) {
            GenericIO.writelnString("Operation fails on creating the " + logName + " file!");
            System.exit(1);
        }

        log.writelnString("         AFTERNOON AT THE RACE TRACK - Description of the internal state of the problem\n\n"
                + "MAN/BRK          SPECTATOR/BETTER             HORSE/JOCKEY PAIR at Race RN\n"
                + "  Stat  St0 Am0  St1 Am1  St2 Am2  St3 Am3   RN St0 Len0 St1 Len1 St2 Len2 St3 Len3"

        );

        log.writelnString(
                "                                    Race RN Status\n"
                        + " RN Dist BS0 BA0 BS1 BA1 BS2 BA2 BS3 BA3 Od0 N0 Ps0 SD0 Od1 N1 Ps1 Sd1 Od2 N2 Ps2 Sd2 Od3 N3 Ps3 St3"
        );

        if (!log.close()) {
            GenericIO.writelnString("Operation fails on creating the " + logName + " file!");
            System.exit(1);
        }
    }

    /**
     * Report actual status
     *
     */
    @Override
    public synchronized void reportStatus() {

        String stat = "  ";

        if (!log.openForAppending(".", logName)) {
            GenericIO.writelnString("Operation fails on creating the " + logName + " file_1!");
            System.exit(1);
        }


        switch (brokerState) {
            case Constants.OPENING_THE_EVENT:
                stat += "OPEN  ";
                break;
            case Constants.ANNOUNCING_NEXT_RACE:
                stat += "NEXT  ";
                break;
            case Constants.WAITING_FOR_BETS:
                stat += "WBET  ";
                break;
            case Constants.SUPERVISING_THE_RACE:
                stat += "SUPE  ";
                break;
            case Constants.SETTLING_ACCOUNTS:
                stat += "SETA  ";
                break;
            case Constants.PLAYING_HOST_AT_THE_BAR:
                stat += "PLAY  ";
                break;
            default: stat += "----  ";
        }

        for (int i = 1; i <= nrSpectactors; i++) {
            switch (spectatorState[i]) {
                case Constants.WAITING_FOR_A_RACE_TO_START:
                    stat += "WRS ";
                    break;
                case Constants.APPRAISING_THE_HORSES:
                    stat += "ATH ";
                    break;
                case Constants.PLACING_A_BET:
                    stat += "PAB ";
                    break;
                case Constants.WATCHING_A_RACE:
                    stat += "WAR ";
                    break;
                case Constants.COLLECTING_THE_GAINS:
                    stat += "CTG ";
                    break;
                case Constants.CELEBRATING:
                    stat += "CEL ";
                    break;
                default: stat += "--- ";
            }
            if (amountOfMoney[i] != -1){
                if (amountOfMoney[i] > 999)
                    stat += amountOfMoney[i]+" ";
                else if (amountOfMoney[i] < 999 & amountOfMoney[i] > 99)
                    stat += amountOfMoney[i]+"  ";
                else if (amountOfMoney[i] < 99 & amountOfMoney[i] > 9)
                    stat += amountOfMoney[i]+"   ";
                else
                    stat += amountOfMoney[i]+"    ";
            }
            else
            stat += "---- ";
        }

        stat += "  - "; //race number


        for (int i = 1; i <= nrHorses; i++) {
            switch (horseState[i]) {
                case Constants.AT_THE_STABLE:
                    stat += "ATS  ";
                    break;
                case Constants.AT_THE_PADDOCK:
                    stat += "ATP  ";
                    break;
                case Constants.AT_THE_START_LINE:
                    stat += "ASL  ";
                    break;
                case Constants.RUNNING:
                    stat += "RUN  ";
                    break;
                case Constants.AT_THE_FINNISH_LINE:
                    stat += "AFL  ";
                    break;
                default: stat += "---  ";
            }
            if (horseMaxDistance[i] != -1){
                if (horseMaxDistance[i]<10)
                    stat += horseMaxDistance[i]+"   ";
                else
                    stat += horseMaxDistance[i]+"  ";
            }
            else
                stat += "--  ";
        }

        log.writelnString(stat);

        stat = "  ";

        stat += "- "; //race number
        //Race distance in the present race
        if (raceDistance !=0)
            stat += raceDistance+"    ";
        else
            stat += "--    ";

        for (int i = 1; i <= nrSpectactors; i++) {
            if (betSelectionHorse[i] != -1)
                stat += betSelectionHorse[i]+" ";
            else
                stat += "- ";

            if (betAmount[i] != -1){
                if (betAmount[i] > 999)
                    stat += betAmount[i]+"  ";
                else if (betAmount[i] < 999 & betAmount[i] > 99)
                    stat += betAmount[i]+"   ";
                else if (betAmount[i] < 99 & betAmount[i] > 9)
                    stat += betAmount[i]+"    ";
                else
                    stat += betAmount[i]+"     ";
            }
            else
                stat += "----  ";
        }
        //"  Stat  St0 Am0  St1 Am1  St2 Am2  St3 Am3   RN St0 Len0 St1 Len1 St2 Len2 St3 Len3"
        //"RN Dist BS0 BA0  BS1 BA1  BS2 BA2  BS3 BA3 Od0 N0 Ps0 SD0 Od1 N1 Ps1 Sd1 Od2 N2 Ps2 Sd2 Od3 N3 Ps3 St3"
        for (int i = 1; i <= nrHorses; i++) {
            if (winningProbability[i] != null){
                if(winningProbability[i]=="1" || winningProbability[i]=="0" )
                    stat += winningProbability[i]+"   ";
                else
                    stat += winningProbability[i]+" ";
            }
            else
                stat += "---- ";

            if (horseSteps[i] != -1){
                if (horseSteps[i]<10)
                    stat += horseSteps[i]+"  ";
                else
                    stat += horseSteps[i]+" ";
            }
            else
                stat += "-- ";

            if (horsePosition[i] != -1){
                if (horsePosition[i]<10)
                    stat += " "+horsePosition[i]+"  ";
                else
                    stat += horsePosition[i]+"  ";
            }
            else
                stat += "--  ";

            stat += "--  ";

        }

        log.writelnString(stat);
        if (!log.close()) {
            GenericIO.writelnString("Operation fails on creating the " + logName + " file!_2");
            System.exit(1);
        }
    }

    /**
     * Get broker state
     *
     * @return brokerState - State of the Broker
     */
    @Override
    public synchronized int getBrokerState() {
        return brokerState;
    }

    /**
     * Set broker state
     *
     * @param brokerState State of the Broker
     */
    @Override
    public synchronized void setBrokerState(int brokerState) {
        this.brokerState = brokerState;
    }

    /**
     * Set Spectator State
     *
     * @param id Spectator ID
     * @param spectatorState Spectator State
     */
    @Override
    public synchronized void setSpectatorState(int id, int spectatorState) {
        this.spectatorState[id] = spectatorState;
    }

    /**
     * Set Horse state
     *
     * @param id Horse ID
     * @param horseState Horse State
     */
    @Override
    public synchronized void setHorseState(int id, int horseState) {
        this.horseState[id] = horseState;
    }

    /**
     * Set Horse Max Distance
     *
     * @param id Horse ID
     * @param horseMaxDistance Horse's maximum distance
     */
    @Override
    public synchronized void setHorseMaxDistance(int id, int horseMaxDistance) {
        this.horseMaxDistance[id] = horseMaxDistance;
    }

    /**
     * Get number of Horses
     *
     * @return nrHorses - Number of Horses
     */
    @Override
    public synchronized int getNrHorses() {
        return nrHorses;
    }

    /**
     * Set number of Horses
     *
     * @param nrHorses Number of Horses
     */
    @Override
    public synchronized void setNrHorses(int nrHorses) {
        this.nrHorses = nrHorses;
    }

    /**
     * Get the number of Spectators
     *
     * @return nrSpectators - Number of Spectators
     */
    @Override
    public synchronized int getNrSpectactors() {
        return nrSpectactors;
    }

    /**
     * Set the number of Spectators
     *
     * @param nrSpectactors Number of Spectators
     */
    @Override
    public synchronized void setNrSpectactors(int nrSpectactors) {
        this.nrSpectactors = nrSpectactors;
    }

    /**
     * Get the number of Races
     *
     * @return nrRaces - Number of Races
     */
    @Override
    public synchronized int getNrRaces() {
        return nrRaces;
    }

    /**
     * Set the number of Races
     *
     * @param nrRaces Number of Races
     */
    @Override
    public synchronized void setNrRaces(int nrRaces) {
        this.nrRaces = nrRaces;
    }

    /**
     * Get the race distance
     *
     * @return raceDistance - Race Distance
     */
    @Override
    public synchronized int getRaceDistance() {
        return raceDistance;
    }

    /**
     * Set the Ammount of money for spectator
     *
     * @param nrSpectactors Number of Spectators
     * @param amountOfMoney Total ammount of money available for bets
     */
    @Override
    public synchronized void setAmountOfMoney(int nrSpectactors, int amountOfMoney) {
       this.amountOfMoney[nrSpectactors] = amountOfMoney;
    }

    /**
     * Set the Bet selected horse
     *
     * @param nrSpectactors Number of Spectators
     * @param betSelectionHorse Horse selected for bet
     */
    @Override
    public synchronized void setBetSelectionHorse(int nrSpectactors, int betSelectionHorse) {
        this.betSelectionHorse[nrSpectactors] = betSelectionHorse;
    }

    /**
     * Set the Bet ammount
     *
     * @param nrSpectactors Number of Spectators
     * @param betAmount Bet Ammount
     */
    @Override
    public synchronized void setBetAmount(int nrSpectactors, int betAmount)
    {
        this.betAmount[nrSpectactors] = betAmount;
    }

    /**
     * Set the Winning prob
     *
     * @param nrHorses Number of Horses
     * @param winningProbability Winning probability of the Horse
     */
    @Override
    public synchronized void setWinningProbability(int nrHorses, String winningProbability) {
        this.winningProbability[nrHorses] = winningProbability;
    }

    /**
     * Set the Horse move
     *
     * @param id Horse ID
     * @param horseSteps Horse move
     */
    @Override
    public synchronized void setHorseSteps(int id, int horseSteps) {
        this.horseSteps[id] = horseSteps;
    }

    /**
     * Set the horse Position
     *
     * @param id Horse ID
     * @param horsePosition Horse Position
     */
    @Override
    public synchronized void setHorsePosition(int id, int horsePosition) {
        this.horsePosition[id] = horsePosition;
    }

}
