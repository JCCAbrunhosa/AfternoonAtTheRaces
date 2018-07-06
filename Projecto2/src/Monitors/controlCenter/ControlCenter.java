package Monitors.controlCenter;

import Extras.Constants;
import Monitors.repository.Repository;
import Monitors.repository.RepositoryInterface;
import comInf.ClientCom;
import comInf.PortsCom;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Local class Control Center
 */
public class ControlCenter implements ControlCenterInterface {

    /**
     * Repository
     *
     * @serialField repository
     */
    private RepositoryInterface repository;

    /**
     * Last horse on paddock
     *
     * @serialField lastHorseOnPaddock
     */
    private boolean lastHorseOnPaddock;

    /**
     * Number of Spectators
     *
     * @serialField nrSpectators
     */
    private int nrSpectactors;

    /**
     * Last spectator
     *
     * @serialField lastSpectator
     */
    private boolean lastSpectator;

    /**
     * Reported the results
     *
     * @serialField reportedResults
     */
    private boolean reportedResults;

    /**
     * A list containing the winners for each race
     *
     * @serialField winnersList
     */
    private ArrayList<Integer> winnersList;

    /**
     * All spectators are ready
     *
     * @serialField allSpecsReady
     */
    private int allSpecsReady;

    /**
     * All have reported
     *
     * @serialField allReported
     */
    private boolean allReported;

    /**
     * All ready
     *
     * @serialField allReady
     */
    private int allReady;


    /**
     * Control Center constructor
     *
     * @param repository
     */
    public ControlCenter(RepositoryInterface repository) {
        this.repository =repository;
        nrSpectactors = 0;
        winnersList = new ArrayList<>();
        lastHorseOnPaddock = false;
        lastSpectator = false;
        reportedResults = false;
        allReported=false;
        allSpecsReady = 0;
        allReady=0;
    }

    /**
     * Waiting for next race
     *
     * @param specId
     *
     * Used by: Spectator
     */
    @Override
    public synchronized void waitForNextRace(int specId) {

        repository.setSpectatorState(specId, Constants.WAITING_FOR_A_RACE_TO_START);

        repository.reportStatus();


        while (lastHorseOnPaddock == false) {
            try {
                wait();
            } catch (Exception e) {
            }
        }

        nrSpectactors++;

        System.out.println("Spectator " + specId + " going to paddock! - Spectator");
        //When all spectators are here they are waken up
        if (nrSpectactors == repository.getNrSpectactors()) {
            lastSpectator = true;
            lastHorseOnPaddock = false;
            nrSpectactors=0;
            notifyAll();
        }


    }

    /**
     * Going to watch the race
     *
     * @param specId
     *
     * Used by: Spectator
     */

    @Override
    public synchronized void goWatchTheRace(int specId) {

        repository.setSpectatorState(specId, Constants.WATCHING_A_RACE);
        repository.reportStatus();

        allReady++;
        notifyAll();

        while (reportedResults == false) {
            try {
                wait();
            } catch (Exception e) {
            }
        }

        allSpecsReady++;
        if(allSpecsReady==repository.getNrSpectactors()){
            allReported=true;
            notifyAll();
        }


    }

    /**
     * Reports the results for each race
     *
     * @param betList
     * @param winnerHorse
     * @return ArrayList containing the winners
     *
     * Used by: Broker
     */
    @Override
    public synchronized ArrayList reportResults(HashMap<Integer, Integer> betList, int winnerHorse) {

        winnersList.clear();
        for (int i = 1; i <= betList.size(); i++) {
            if (betList.get(i) == winnerHorse) {
                System.out.println("Spectator " + i + " has won the bet for horse " + winnerHorse + " !");
                winnersList.add(i);
            }

        }

        //Wakes up the spectators
        reportedResults = true;
        notifyAll();

        while(!allReported){
            try{
                wait();
            }catch(Exception e){}
        }

        allReported=false;
        reportedResults=false;
        allSpecsReady=0;

        return winnersList;
    }

    /**
     * Checks if all the horses are on Paddock
     *
     * Used by: Broker
     */

    @Override
    public synchronized void allHorsesOnPaddock() {

        lastHorseOnPaddock = true;
        notifyAll();

        while (lastSpectator == false) {
            try {

                wait();
            } catch (Exception e) {

            }
        }
        lastHorseOnPaddock = false;
        lastSpectator = false;
        nrSpectactors = 0;
        System.out.println("All Spectators are going to Paddock! - Broker");
    }


    /**
     * Sees if he has won the bet
     *
     * @param specId
     * @return Boolean showing if he won or not
     *
     * Used by: Spectator
     */
    @Override
    public synchronized boolean haveIWon(int specId) {

        repository.setSpectatorState(specId, Constants.WATCHING_A_RACE);
        repository.reportStatus();

        if (winnersList.contains(specId) == true) {
            return true;
        }
        return false;
    }

    /**
     * Entertain the guests
     *
     * Used by: Broker
     */
    @Override
    public synchronized void entertainTheGuests() {

        repository.setBrokerState(Constants.PLAYING_HOST_AT_THE_BAR);
        repository.reportStatus();

    }

    /**
     * Relax after the race
     *
     * @param specId
     * @param finalValue
     *
     * Used by: Spectator
     */
    @Override
    public synchronized void relaxABit(int specId, double finalValue) {
        repository.setSpectatorState(specId, Constants.CELEBRATING);
        repository.reportStatus();

        System.out.println("Spectator " + specId + " has finished with a total ammount of " + finalValue);

    }


    /**
     * Checks if spectators are ready
     *
     * Used by: Broker
     */
    @Override
    public synchronized void allSpectatorsReady() {

        while (allReady != repository.getNrSpectactors()) {
            try{
                wait();
            }catch(Exception e){}

        }
        allReady=0;

    }
}


