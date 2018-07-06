package Entities;


import Monitors.bettingCenter.BettingCenter;
import Monitors.bettingCenter.BettingCenterInterface;
import Monitors.controlCenter.ControlCenter;
import Monitors.controlCenter.ControlCenterInterface;
import Monitors.paddock.Paddock;
import Monitors.paddock.PaddockInterface;
import Monitors.repository.Repository;
import Monitors.repository.RepositoryInterface;

import java.util.HashMap;

import java.util.Random;

/**
 * Spectator Thread
 */

public class Spectator extends Thread {

    /**
     * ID of the Spectator
     *
     * @serialField id
     */

    private int id;

    /**
     * Value available for the bets
     *
     * @serialField valueForBets
     */

    private double valueForBets;

    /**
     * List that connects the value bet to the respective horse
     *
     * @serialField bets
     */

    private HashMap<Integer,Double> bets; //Associa o valor apostado com o cavalo

    /**
     * Paddock
     *
     * @serialField paddock
     */

    private PaddockInterface paddock;

    /**
     * Betting Center
     *
     * @serialField btCenter
     */

    private BettingCenterInterface btCenter;

    /**
     * Control Center
     *
     * @serialField cntrlCenter
     */

    private ControlCenterInterface cntrlCenter;

    /**
     * Repository
     *
     * @serialField repo
     */

    private RepositoryInterface repo;

    /**
     * Connects the horse to its respective winning chances
     *
     * @serialField winningChances
     */

    private HashMap<Integer, Double> winningChances;

    /**
     * ID of the horse to bet
     *
     * @serialField horseToBet
     */

    private int horseToBet;

    /**
     * Possible gains after the bet
     *
     * @serialField possibleGain
     */

    private double possibleGain;

    /**
     * Spectator Constructor
     *
     * @param id Spectator ID
     * @param valueForBets Value available for bets
     * @param paddock Paddock
     * @param btCenter Betting Center
     * @param cntrlCenter Control Center
     * @param repo Repository
     */

    public Spectator(int id, double valueForBets, PaddockInterface paddock, BettingCenterInterface btCenter, ControlCenterInterface cntrlCenter, RepositoryInterface repo){

        this.id=id;
        this.valueForBets=valueForBets;
        bets = new HashMap<>();
        winningChances=new HashMap<>();
        this.paddock=paddock;
        this.btCenter=btCenter;
        this.cntrlCenter=cntrlCenter;
        this.repo=repo;
        this.possibleGain=0;

    }

    /**

     * Lifecyle of the Spectator

     */

    @Override

    public void run(){

        while(repo.getNrRaces()>0) {
            //Waits for next race
            cntrlCenter.waitForNextRace(id);
            Integer amountOfMoney=Math.abs((int) (valueForBets));
            repo.setAmountOfMoney(id, amountOfMoney);

            //Goes and checks horses
            horseToBet = paddock.goCheckHorses(id);


            winningChances = paddock.getWinningChances();

            //Going to bet
            double valueToBet = Math.abs((double) (Math.random() * ((valueForBets + 1))) + 1);


            valueForBets = valueForBets - valueToBet;


            if (valueForBets <= 0.0) {
                valueToBet = 0.0;
                valueForBets=0.0;
            }
            bets.put(horseToBet, valueToBet);

            possibleGain = (valueToBet / ((winningChances.get(horseToBet))));
            if(possibleGain<=0)
                possibleGain=0;

            System.out.println("Spectator " + id + " bets " + valueToBet + " on the horse " + horseToBet + " (Winning Change: " + winningChances.get(horseToBet) + " %) - Possible gains: " + possibleGain);

            //Placing a bet
            btCenter.placeABet(id, valueToBet, horseToBet, possibleGain);

            //Going to watch the race
            cntrlCenter.goWatchTheRace(id);

            //Sees if he has won
            if (cntrlCenter.haveIWon(id)==true) {
                valueForBets += btCenter.goCollectTheGains(id);
                //System.out.println("Spectator " + id + " now has " + valueForBets + " to bet!");
            }

            if(valueForBets<=0){
                System.out.println("Spectator " + id +" has no money!" );
            }

        }
        Integer amountOfMoney=Math.abs((int) (valueForBets));
        repo.setAmountOfMoney(id, amountOfMoney);

        cntrlCenter.relaxABit(id, valueForBets);

    }

}
