package Monitors.bettingCenter;

import Extras.Constants;
import Monitors.repository.Repository;
import Monitors.repository.RepositoryInterface;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Local class Betting Center
 */
public class BettingCenter implements BettingCenterInterface {

    /** Repository
     *
     *@serialField repository
     */
     RepositoryInterface repository;

    /**
     * List by Spectator
     *
     * @serialField listBySpectator
     */
    private HashMap<Integer, Integer> listBySpectator;

    /**
     * List of bets (Spectator ID, Horse ID), Bet Value
     *
     * @serialField bets
     */
    private HashMap<HashMap<Integer,Integer>, Double> bets;
    /**
     * Possible Gains User, Win Value
     *
     * @serialField possibleGains
     */
    private HashMap<Integer, Double> possibleGains;

    /**
     * Connects the spectator to the ammount they have available for bets
     *
     * @serialField moneyList
     */
    private HashMap<Integer, Integer> moneyList; //HashMap that connects Spectator to the total ammount they have available for bets

    /**
     * Number of Spectators
     *
     * @serialField nrOfSpectators
     */
    private int nrOfSpectators;

    /**
     * Number of Bets already collected by the Broker
     *
     * @serialField nrOfBetsCollected
     */
    private int nrOfBetsCollected;

    /**
     * List of winners
     *
     * @serialField winnersList
      */
    private ArrayList<Integer> winnersList;

    private int specId;
    private double betValue;
    private int horseId;

    /**
     * Indicates if final bet was done
     *
     * @serialField finalBet
     */
    private boolean finalBet;

    /**
     * Indicates if the broker is accepting bets
     *
     * @serialField acceptsTheBet
     */
    private boolean acceptsTheBet;

    /**
     * Indicates if a new bet was done
     *
     * @serialField newBetDone
     */
    private boolean newBetDone;

    /**
     * Indicates if all the spectators already collected the gains (if on the Winners list)
     *
     * @serialField allCollectedTheGains
     */
    private boolean allCollectedTheGains;

    /**
     * Indicates if the broker is honouring the bets
     *
     * @serialField honouringTheBets
     */
    private boolean honouringTheBets;



    /**
     * Betting Center constructor
     *
     * @param repository
     */
    public BettingCenter(RepositoryInterface repository){

        this.repository=repository;
        nrOfSpectators=0;
        nrOfBetsCollected=0;
        listBySpectator=new HashMap<>();
        winnersList=new ArrayList<>();
        bets=new HashMap<>();
        moneyList=new HashMap<>();
        possibleGains=new HashMap<>();
        finalBet=false;
        newBetDone=false;

    }

    /**
     * Method that accepts the bets, waking up spectators one by one
     *
     * Used by: Broker
     */
    @Override
    public synchronized void acceptTheBets() {

        System.out.println("Waiting for bets! - Broker");

        repository.setBrokerState(Constants.WAITING_FOR_BETS);
        repository.reportStatus();

        //Create a new bulletin for the next race bets
        bets=new HashMap<>();
        acceptsTheBet=true;
        notifyAll();

        while(!finalBet && !newBetDone){
            try{

                wait();
            }catch(Exception e){}
        }


        System.out.println("All bets are on! Starting the race! - Broker");

    }

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

    @Override
    public synchronized void placeABet(int specId, double betValue, int horseId, double possibleGain){

        nrOfSpectators++;

        if(betValue<=0)
            betValue=0;

        //The spectator needs to wait for the Broker to accept his bet
        while(acceptsTheBet==false){
            try{
                notifyAll();
                wait();
            }catch(Exception e){}
        }

        //Going to the watching stand
        System.out.println("Spectator number " + specId + " going to the watching stand to watch the Race! - Spectator");

        listBySpectator.put(specId,horseId);
        bets.put(listBySpectator,betValue);
        possibleGains.put(specId,possibleGain);

        repository.setSpectatorState(specId,Constants.PLACING_A_BET);
        repository.setBetSelectionHorse(specId,horseId);
        Integer betAmount=Math.abs((int) (betValue));
        repository.setBetAmount(specId,betAmount);
        repository.reportStatus();


        //Notify that a new bet is due
        newBetDone=true;
        notifyAll();


        if(nrOfSpectators==repository.getNrSpectactors()){
            nrOfSpectators=0;
            finalBet=true;
            newBetDone=false;
            notifyAll();
        }
        finalBet=false;
    }

    /**
     * Method that returns the winning money if the spectator is on the winners list
     *
     * Used by: Spectator
     *
     * @param specId
     * @return
     */

    @Override
    public synchronized double goCollectTheGains(int specId){

        repository.setSpectatorState(specId,Constants.COLLECTING_THE_GAINS);
        repository.reportStatus();


        //The spectator waits until the Broker says they can withdrawal their bet winnings
        while(honouringTheBets==false){
            try{
                wait();
            }catch(Exception e){}
        }
        notifyAll();


        nrOfBetsCollected++;

        if(nrOfBetsCollected==winnersList.size()){
            allCollectedTheGains=true;
            notifyAll();
        }


        return possibleGains.get(specId);
    }

    /**
     * Method that gives the winners their prize
     *
     * Used by: Broker
     *
     * @param winnersList
     */

    @Override
    public synchronized void honourTheBets(ArrayList<Integer> winnersList){


        this.winnersList=winnersList;

        if(winnersList.size()>0){
            while(allCollectedTheGains==false){
                honouringTheBets=true;
                notifyAll();
                try{
                    wait();
                }catch(Exception e){}
            }

            honouringTheBets=false;
            allCollectedTheGains=false;
            nrOfBetsCollected=0;


            System.out.println("All bets have been honoured!");
        }else{
            System.out.println("There are no winners!!");
            nrOfBetsCollected=0;
            honouringTheBets=false;
            notifyAll();
        }

        acceptsTheBet=false;

    }

    /**
     * Method that returns the complete bet list to the Broker to see who has won or not
     *
     * Used by: Broker
     * @return
     */

    @Override
    public synchronized HashMap areThereAnyWinners(int winnerHorse) {

        repository.setBrokerState(Constants.SETTLING_ACCOUNTS);
        repository.reportStatus();

        for (int i = 1; i <= listBySpectator.size(); i++) {
            if (listBySpectator.get(i) == winnerHorse) {
                System.out.println("There are winners!");
            }
            return listBySpectator;
        }
        return null;
    }

    /**
     * Method that certifies if all spectators are ready to bet
     *
     * Used by: Broker
     */
    @Override
    public synchronized void areSpectatorsReadyToBet(){

        while(nrOfSpectators<repository.getNrSpectactors()){
            try{
                wait();
            }catch(Exception e){}
        }
    }

    //Envio das messagens relacionadas para o servidor



}
