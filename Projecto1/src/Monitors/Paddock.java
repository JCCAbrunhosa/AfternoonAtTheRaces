package Monitors;

import Extras.Constants;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

/**
 *
 */
public class Paddock implements PaddockInterface {

    /**
     * Repository
     *
     * @serialField Repository
     */
    private Repository repository;

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
     * Paddock Constructor
     *
     * @param repository Repository
     */
    public Paddock(Repository repository){
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
    public synchronized int goCheckHorses(int specId) {

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
     *
     *
     */
    @Override
    public synchronized void proceedToPaddock(int horseId, double horseWinningChance){

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
     *
     */
    @Override
    public synchronized HashMap getWinningChances(){
        return winningChances;
    }




}
