package serverSide;
import extras.Constants;
import interfaces.*;
import interfaces.Register;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Local class racing track
 */
public class RacingTrack implements RacingTrackInterface {

    /**
     *  Repository
     *
     *  @serialField repository
     */
    private RepositoryInterface repository;

    /**
     * Race track distance
     *
     * @serialField raceDistance
     */
    private int raceDistance;

    /**
     * Indicates if the horse is ready to Start
     *
     * @serialField readyToRace
     */
    private boolean readyToRace;

    /**
     * Number of horses at the startLine
     *
     * @serialField nrHorsesAtTheStartLine
     */
    private int nrHorsesAtStartLine;


    /**
     * Positions of each horse
     *
     * @serialField horsePositions
     */
    private HashMap<Integer, Integer> horsesPositions;

    /**
     * Horses ID in a list
     *
     * @serialField horses
     */
    private ArrayList<String> horses;

    /**
     * Indicates if it is the last horse
     *
     * @serialField lastHorse
     */
    private boolean lastHorse;

    /**
     * Id of the horses that are running
     *
     * @serialField horseToRunId
     */
    private int horseToRunId;

    /**
     * New position of the horse after an increment
     *
     * @serialField newPosition
     */
    private int newPosition;

    /**
     * Winner horse ID
     *
     * @serialField winnerHorse
     */
    private int winnerHorse;

    /**
     * Number of horses racing
     *
     * @serialField nrOfHorsesRacing
     */
    private int nrOfHorsesRacing;

    private Register register;



    /**
     * Racing Track Constructor
     *
     * @param repository
     */
    public RacingTrack(RepositoryInterface repository, Register register) throws RemoteException {
        this.repository=repository;
        this.register=register;
        nrHorsesAtStartLine=0;
        horsesPositions=new HashMap<>();
        nrOfHorsesRacing=repository.getNrHorses();
        horses=new ArrayList<>();
        raceDistance = repository.getRaceDistance();
        lastHorse=false;

    }


    /**
     * Method used by the horse to proceed to start line
     *
     * Used by: Horse
     *
     * @param horseId
     */
    @Override
    public synchronized void proceedToStartLine(int horseId) throws RemoteException {
        nrHorsesAtStartLine++;
        System.out.println("Horse number "  + horseId + " waiting for the Broker to start the Race! - Horse");

        //Wakes up the spectators
        if(nrHorsesAtStartLine==repository.getNrHorses()){
            notifyAll();
        }


        //horses should wait for the go
        while(readyToRace==false){
            try{
                wait();
            }catch(Exception e){

            }
        }
    }

    /**
     * Starts the Race
     *
     * Used by: Broker
     */
    @Override
    public synchronized void startRace() throws RemoteException {

        repository.setBrokerState(Constants.SUPERVISING_THE_RACE);
        repository.reportStatus();
        System.out.println("RACE STARTS!");
        System.out.println("Distance: " + repository.getRaceDistance());

        //Begin with clearing the previous positions of each horse
        for(int i=1; i<=repository.getNrHorses();i++){
            horsesPositions.put(i,0);
            horses.add(String.valueOf(i));
        }

        winnerHorse=0;
        horseToRunId=1;
        readyToRace=true;
        nrOfHorsesRacing=repository.getNrHorses();
        //Wake up horses
        notifyAll();


        while(lastHorse==false){
            try{
                wait();
            }catch (Exception e){

            }
        }

        lastHorse=false;
        readyToRace=false;
        repository.setNrRaces(repository.getNrRaces()-1);
    }

    /**
     * Makes a move in the racing track
     *
     * Used by: Horse
     *
     * @param horseId
     * @param maxDistance
     */
    @Override
    public synchronized void makeAMove(int horseId, int maxDistance) throws RemoteException {

        repository.setHorseState(horseId,Constants.RUNNING);
        repository.reportStatus();

        while(horseToRunId!=horseId){
            try{
                wait();
            }catch (Exception e){

            }
        }

        //Randomize its movement
        int randomDistance= (int) (Math.random() * ((maxDistance + 1))) + 1;

        //Updates the horse position by adding to its previous position
        newPosition=horsesPositions.get(horseId)+ randomDistance;
        repository.setHorseSteps(horseId,randomDistance);
        repository.setHorsePosition(horseId,newPosition);
        repository.reportStatus();

        if(newPosition>repository.getRaceDistance())
            newPosition=repository.getRaceDistance();

        System.out.println("Horse " + horseId + " has moved to " + newPosition);
        horsesPositions.put(horseId,newPosition);

       // System.out.println("Next horse to race: " + horseToRunId);

        //Wakes up the next horse


    }

    /**
     * Checks if the finish line has been crossed
     *
     * Used by: Horse
     *
     * @param horseId
     * @return boolean
     */
    @Override
    public synchronized boolean hasFinishingLineBeenCrossed(int horseId) throws RemoteException {
        boolean finished = false;
        if(horsesPositions.get(horseId)>=raceDistance){

            repository.setHorseState(horseId,Constants.AT_THE_FINNISH_LINE);
            repository.reportStatus();

            if(horsesPositions.containsKey(horseId)==true){
                horsesPositions.remove(horseId);
                nrOfHorsesRacing--;
                finished=true;
            }

            if(nrOfHorsesRacing==0){
                System.out.println("Horse "+ horseId + " is the last one to cross the finish line!");
                System.out.println("Horse " + winnerHorse + " is the winner!!!");
                lastHorse=true;
            }

            if(winnerHorse==0){
                System.out.println("Horse "+ horseId +" is the first one to cross the finish line!" );
                winnerHorse=horseId; //Horse that wins the race
            }
            else{
                System.out.println("Horse "+ horseId + " has crossed the finish line!");
            }

        }
        horseToRunId++;
        while(horsesPositions.containsKey(horseToRunId)==false && horsesPositions.size()!=0){
            horseToRunId++;
            if(horseToRunId>repository.getNrHorses())
                horseToRunId=1;
        }
        notifyAll();
        return finished;
    }

    /**
     * Returns the Winner horse id
     *
     * Used by: Broker
     *
     * @return winnerHorse
     */
    @Override
    public synchronized int getWinnerHorseId(){
        return winnerHorse;
    }

    /**
     * Close the monitor
     *
     *
     */
    public void close() throws RemoteException {

        try {
            register.unbind("RacingTrack");
        } catch (NotBoundException ex) {
            //Logger.getLogger(Stable.class.getName()).log(Level.SEVERE, null, ex);
        }

        Timer X = new Timer();
        X.schedule(new TimerTask() {

            @Override
            public void run() {
                System.exit(0);
            }
        }, 5000);

    }
}
