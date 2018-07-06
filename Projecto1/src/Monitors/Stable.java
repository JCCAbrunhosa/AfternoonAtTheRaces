package Monitors;

import Extras.Constants;

/**
 *
 */
public class Stable implements StableInterface {

    /**
     * Repository
     *
     * @serialField repository
     */
    private Repository repository;

    /**
     * Number of horses
     *
     * @serialField nrHorses
     */
    private int nrHorses;

    /**
     * Broker has called the horses
     *
     * @serialField brokerCallHorses
     */
    private boolean brokerCallHorses;

    /**
     * Stable constructor
     *
     * @param repository Repository
     */
    public Stable(Repository repository){
        this.repository=repository;
        this.nrHorses=0;
        brokerCallHorses=false;
    }

    /**
     * Summon all horses to paddock
     *
     *
     */
    @Override
    public synchronized void summonHorsesToPaddock(){

        repository.setBrokerState(Constants.ANNOUNCING_NEXT_RACE);
        repository.reportStatus();
        System.out.println("RACE NUMBER: " + repository.getNrRaces());
        System.out.println("Calling Horses to Paddock! - Manager");
        brokerCallHorses=true;

        //Wakes up waiting horses
        notifyAll();
        //Waits for all the horses to go to Paddock
        while(nrHorses!=repository.getNrHorses()){
            try{
                wait();
            }catch (Exception e){

            }
        }

        System.out.println("All horses are on the Paddock! - Manager");
        nrHorses=0;
        brokerCallHorses=false;
    }

    /**
     * Proceed to the stable
     *
     * @param horseId Horse ID
     *
     *
     */
    @Override
    public synchronized void proceedToStable(int horseId){
        repository.setHorseState(horseId,Constants.AT_THE_STABLE);
        repository.reportStatus();

        while(brokerCallHorses==false){
            try{
                wait(); //Horses are still on the Stable
            }catch(Exception e){

            }
        }

        //Increments the number of horses inside the stable
        nrHorses++;
        repository.setHorseState(horseId,Constants.AT_THE_PADDOCK);
        repository.reportStatus();
        System.out.println("Horse number " + horseId + " going to Paddock! - Horse" );

        //Wakes up broker
        notifyAll();
    }
}
