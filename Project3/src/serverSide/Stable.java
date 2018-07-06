package serverSide;
import extras.Constants;
import interfaces.*;
import interfaces.Register;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Local class Stable
 */
public class Stable implements StableInterface {

    /**
     * Repository
     *
     * @serialField repository
     */
    private RepositoryInterface repository;

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

    private Register register;

    /**
     * Stable constructor
     *
     * @param repository Repository
     */
    public Stable(RepositoryInterface repository, Register register){
        this.repository=repository;
        this.nrHorses=0;
        this.register=register;
        brokerCallHorses=false;
    }

    /**
     * Summon all horses to paddock
     *
     *
     */
    @Override
    public synchronized void summonHorsesToPaddock() throws RemoteException {

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
     */
    @Override
    public synchronized void proceedToStable(int horseId) throws RemoteException {
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
    /**
     * Close the monitor
     *
     *
     */
    public void close() throws RemoteException {

        try {
            register.unbind("Stable");
        } catch (NotBoundException ex) {
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
