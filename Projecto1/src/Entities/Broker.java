package Entities;

import Extras.Constants;
import Monitors.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 */
public class Broker extends Thread {

    /**
     * Racing track
     *
     * @serialField rt
     */
    private RacingTrack rt;
    /**
     * Stable
     *
     * @serialField stable
     */
    private Stable stable;
    /**
     * Betting center
     *
     * @serialField bc
     */
    private BettingCenter bc;
    /**
     * Control Center
     *
     * @serialField cc
     */
    private ControlCenter cc;
    /**
     * Repository
     *
     * @serialField repo
     */
    private Repository repo;


    private Paddock paddock;

    /**
     * Broker Constructor
     *
     * @param rt Racing track
     * @param stable stable
     * @param bc Betting center
     * @param cc Control Center
     * @param paddock Paddock
     * @param repo Repository
     */
    public Broker(RacingTrack rt, Stable stable, BettingCenter bc, ControlCenter cc, Paddock paddock, Repository repo) {
        this.rt = rt;
        this.stable = stable;
        this.bc = bc;
        this.cc = cc;
        this.repo = repo;
        this.paddock=paddock;
    }

    /**
     * Broker life cycle
     * Manages the bets, pays the dividends in case of victory
     */
    @Override
    public void run() {
        repo.setBrokerState(Constants.OPENING_THE_EVENT);
        repo.reportStatus();
        while(repo.getNrRaces()>0) {
            //First the broker summons all horses to Paddock
            stable.summonHorsesToPaddock();

            //Verifies that all horses are on paddock
            //Broker confirms all horses are on paddock(waking up )
            cc.allHorsesOnPaddock();
            bc.areSpectatorsReadyToBet();

            //Now he's ready to accept the bets
            //state=BrokerStates.WAITING_FOR_BETS;
            bc.acceptTheBets();

            cc.allSpectatorsReady();
            //Starts the Race
            rt.startRace();

            int winnerHorseId = rt.getWinnerHorseId();
            //Reports the results (first he gets the list from the betting center)
            HashMap<Integer, Integer> betList = bc.areThereAnyWinners(winnerHorseId);
            ArrayList<Integer> winnersList=new ArrayList<>();
            if(betList!=null){
                winnersList = cc.reportResults(betList, winnerHorseId);
            }

           /* for(int i=0;i<winnersList.size();i++){
                System.out.println("Winners: " + winnersList.get(i));
            }*/

            //Honours the bets
            bc.honourTheBets(winnersList);

        }

        cc.entertainTheGuests();

        System.out.println("RACES HAVE FINISHED!!!!!");

    }

}
