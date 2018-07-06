package Main;
/*
import Entities.Broker;
import Entities.Horse;
import Entities.Spectator;
import Monitors.bettingCenter.BettingCenter;
import Monitors.controlCenter.ControlCenter;
import Monitors.paddock.Paddock;
import Monitors.racingTrack.RacingTrack;
import Monitors.repository.Repository;
import Monitors.stable.Stable;

import java.util.ArrayList;
import java.util.Random;


public class Race {

    public static void main (String [] args) throws InterruptedException {

        //Initiate variables
        int nrHorses = 4
        int nrSpectators = 4;
        int nrRaces = 4;
        Horse horse = null;
        Spectator spectator = null;
        ArrayList<Spectator> listSpectator = new ArrayList<>();
        ArrayList<Horse> listHorse = new ArrayList<>();
        Random rand = new Random();
        double winningChance = 0;
        double totalChance = 1.0;
        int raceDistance = 10 + rand.nextInt((95 - 10) + 1);//Distance varies between 10 and 95;

        //First create the repository (falta uma classe para escrever o log file);
        Repository repository = new Repository(nrHorses, nrSpectators, nrRaces, new Random().nextInt(raceDistance), "");
        ControlCenter controlCenter = new ControlCenter(repository);
        Stable stable = new Stable(repository);
        Paddock paddock = new Paddock(repository);
        BettingCenter bettingCenter = new BettingCenter(repository);
        RacingTrack racingTrack = new RacingTrack(repository);

        //Set the number of races
        repository.setNrRaces(nrRaces);

        //Then we create the threads
        Broker broker = new Broker(racingTrack, stable, bettingCenter, controlCenter, paddock, repository);
        broker.getPriority();
        broker.start();

        for (int i = 1; i <= nrHorses; i++) {
            rand = new Random();
            winningChance = 0 + (rand.nextDouble() * (totalChance - 0));
            totalChance = totalChance - winningChance;
            if (i == nrHorses)
                winningChance = totalChance;
            System.out.println(i + " has winning change of " + winningChance);

            int maxDistance = 1 + rand.nextInt((10 - 1) + 1);//Distance varies between 1 and 10
            horse = new Horse(i, 0, new Random().nextInt(maxDistance), Math.abs(maxDistance), winningChance, stable, paddock, racingTrack, repository);
            listHorse.add(horse);
            horse.start();
        }

        for (int j = 1; j <= nrSpectators; j++) {
            spectator = new Spectator(j, 400, paddock, bettingCenter, controlCenter, repository);
            listSpectator.add(spectator);
            spectator.start();
        }

        while (true) {
            try {
                broker.join();

                System.exit(0);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
*/