package clientSide;

import interfaces.*;
import genclass.GenericIO;

import java.rmi.RemoteException;


/**
 * Horse thread
 */

public class Horse extends Thread  {

    /**
     * Horse ID
     *
     * @serialField id
     */
    private int id;

    /**
     * Horse Position
     *
     * @serialField pos
     */
    private int pos;

    /**
     * Random Distance the horse can run
     *
     * @serialField rndDistance
     */
    private int rndDistance;

    /**
     * Maximum distante the horse can run (agility)
     *
     * @serialField maxDistance
     */
    private int maxDistance;

    /**
     * The winning chance for this horse
     *
     * @serialField winningChance
     */
    private double winningChance;

    /**
     * Stable
     *
     * @serialField stable
     */
    private StableInterface stable;

    /**
     * Paddock
     *
     * @serialField paddock
     */
    private PaddockInterface paddock;

    /**
     * Racing Track
     *
     * @serialField racingTrack
     */
    private RacingTrackInterface racingTrack;

    /**
     * Repository
     *
     * @serialField repo
     */
    private RepositoryInterface repo;


    /**
     * Horse Constructor
     *
     * @param id Horse ID
     * @param pos Horse Position
     * @param rndDistance Horse move
     * @param maxDistance Horse Maximum distance
     * @param winningChance Horse winning chance
     * @param stable Stable
     * @param paddock Paddock
     * @param racingTrack Racing Track
     * @param repo Repository
     */
    public Horse(int id, int pos, int rndDistance, int maxDistance,double winningChance, StableInterface stable, PaddockInterface paddock, RacingTrackInterface racingTrack, RepositoryInterface repo){
        this.id=id;
        this.pos=pos;
        this.rndDistance=rndDistance;
        this.maxDistance=maxDistance;
        this.winningChance=winningChance;
        this.stable=stable;
        this.paddock=paddock;
        this.repo=repo;
        this.racingTrack=racingTrack;
    }


    /**
     * Horse lifecycle
     */
    @Override
    public void run() {
        //After being created the horse goes to the stable and waits for the broker to wake him up

        try{
            while (repo.getNrRaces()>0) {

            stable.proceedToStable(id);
            repo.setHorseMaxDistance(id, maxDistance);
            //After waking up it proceeds to Paddock

            paddock.proceedToPaddock(id, winningChance);

            //Horse is going to the Start Line
            //state = HorseStates.AT_THE_START_LINE;
            racingTrack.proceedToStartLine(id);

            //Starts running
            do {
                racingTrack.makeAMove(id, maxDistance);
            } while (!racingTrack.hasFinishingLineBeenCrossed(id));

            //table.proceedToStable(id);
            //After the finishing line crossed the horses return to their positions in the stable
        }
    } catch (
    RemoteException e) {
        GenericIO.writelnString ("Remote exception: " + e.getMessage ());
        System.exit (1);
    }

    }

}
