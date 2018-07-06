package serverSide;
import extras.Constants;
import interfaces.*;
import interfaces.Register;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Local class Paddock
 */
public class Paddock implements PaddockInterface {

    /**
     * Repository
     *
     * @serialField Repository
     */
    private RepositoryInterface repository;

    /**
     * Number of horses
     *
     * @serialField nrOfHorses
     */
    private int nrOfHorses;

    /**
     * Checking the horses
     *
     * @serialField checkingHorses
     */
    private boolean checkingHorses;

    /**
     * Number of horses at the start line
     *
     * @serialField horsesAtStartline
     */
    private int horsesAtStartLine;

    /**
     * Number of spectators
     *
     * @serialField nrSpectators
     */
    private int nrSpectators;

    /**
     * Going to bet
     *
     * @serialField goingToBet
     */
    private int goingToBet;

    /**
     * Winning Chances for horses
     *
     * @serialField winningChances
     */
    private HashMap<Integer, Double> winningChances;

    /**
     * Register
     *
     * @serialField register
     */
    private Register register;


    /**
     * Paddock Constructor
     *
     * @param repository Repository
     */
    public Paddock(RepositoryInterface repository, Register register){
        this.register=register;
        this.repository=repository;
        horsesAtStartLine=0;
        nrSpectators=0;
        nrOfHorses=0;
        goingToBet=0;
        winningChances=new HashMap<>();
        checkingHorses=false;
    }

    /**
     * Go check the horses
     * @param specId Spectator ID
     * @return winningChance - Winning chance of the Horse that the spectator is betting on
     *
     *
     */
    @Override
    public synchronized int goCheckHorses(int specId) throws RemoteException {

        nrSpectators++;

        repository.setSpectatorState(specId,Constants.APPRAISING_THE_HORSES);
        repository.reportStatus();


        if (nrSpectators == repository.getNrSpectactors()) {
            checkingHorses = true;

            notifyAll();

            System.out.println("All Spectators on the Paddock!");
        }

        while (horsesAtStartLine != repository.getNrHorses()) {
            try {
                wait();
            } catch (Exception e) {

            }
        }

        //Now spectators are ready to bet
        System.out.println("Spectator number " + specId + " going to bet! - Spectator");
        goingToBet++;

        notifyAll();
        if(goingToBet==repository.getNrSpectactors()){
            horsesAtStartLine=0;
            checkingHorses = false;
            goingToBet=0;
            nrSpectators=0;
            nrOfHorses=0;
        }


        return (int) (Math.random() * ((repository.getNrHorses() - 1))) + 1;


    }

    /**
     * Proceed to Paddock
     *
     * @param horseId Horse ID
     * @param horseWinningChance Horse winning chance
     */
    @Override
    public synchronized void proceedToPaddock(int horseId, double horseWinningChance) throws RemoteException {

        DecimalFormat df = new DecimalFormat("#.##");
        String s = df.format(horseWinningChance);
        repository.setWinningProbability(horseId,s);
        repository.reportStatus();

        //While spectators don't see the horses
        nrOfHorses++;
        winningChances.put(horseId,horseWinningChance);


        while(checkingHorses==false){
            repository.reportStatus();
            try{
                wait(); //Horses are still on the paddock
            }catch(Exception e){

            }
        }

        horsesAtStartLine++;
        if(horsesAtStartLine==repository.getNrHorses()){
            notifyAll();
            checkingHorses=false;
        }
        repository.setHorseState(horseId,Constants.AT_THE_START_LINE);
        repository.reportStatus();
        System.out.println("Horse number " + horseId + " going to Start Line! - Horse");

    }

    /**
     * Get the horses winning chances
     *
     * @return winningChances - List containing the winning chances for all horses
     *
     */
    @Override
    public synchronized HashMap getWinningChances(){
        return winningChances;
    }


    /**
     * Close the monitor
     *
     *
     */
    public void close() throws RemoteException {

        try {
            register.unbind("Paddock");
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
