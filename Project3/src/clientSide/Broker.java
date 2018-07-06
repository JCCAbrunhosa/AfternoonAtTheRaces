package clientSide;

import extras.Constants;
import interfaces.*;
import genclass.GenericIO;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Broker Thread
 */

public class Broker extends Thread {

    /**
     * Racing track
     *
     * @serialField rt
     */
    private RacingTrackInterface rt;
    /**
     * Stable
     *
     * @serialField stable
     */
    private StableInterface stable;
    /**
     * Betting center
     *
     * @serialField bc
     */
    private BettingCenterInterface bc;
    /**
     * Control Center
     *
     * @serialField cc
     */
    private ControlCenterInterface cc;
    /**
     * Repository
     *
     * @serialField repo
     */
    private RepositoryInterface repo;


    private PaddockInterface paddock;

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
    public Broker(RacingTrackInterface rt, StableInterface stable, BettingCenterInterface bc, ControlCenterInterface cc, PaddockInterface paddock, RepositoryInterface repo) {
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
        try {
            repo.setBrokerState(Constants.OPENING_THE_EVENT);
        } catch (RemoteException e) {
            GenericIO.writelnString ("Remote exception: " + e.getMessage ());
            System.exit (1);
        }
        try {
            repo.reportStatus();
        } catch (RemoteException e) {
            GenericIO.writelnString ("Remote exception: " + e.getMessage ());
            System.exit (1);
        }

        try{
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
    } catch (
    RemoteException e) {
        GenericIO.writelnString ("Remote exception: " + e.getMessage ());
        System.exit (1);
    }

        try {
            cc.entertainTheGuests();
        } catch (RemoteException e) {
            GenericIO.writelnString ("Remote exception: " + e.getMessage ());
            System.exit (1);
        }

        System.out.println("RACES HAVE FINISHED!!!!!");
        try {
            stable.close();
            cc.close();
            bc.close();
            paddock.close();
            rt.close();
            repo.close();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

}
