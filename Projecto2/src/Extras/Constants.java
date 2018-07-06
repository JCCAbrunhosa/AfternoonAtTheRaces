package Extras;

/**
 *
 * Constants for the project
 */

public class Constants {
    /**
     *  Broker, Horse and Spectator states
     */
    public final static int
            OPENING_THE_EVENT = 0,              //initial state
            ANNOUNCING_NEXT_RACE = 1,           //
            WAITING_FOR_BETS = 2,               //
            SUPERVISING_THE_RACE = 3,           //
            PLAYING_HOST_AT_THE_BAR = 4,        //
            AT_THE_STABLE = 5,                  //
            AT_THE_PADDOCK = 6,                 //
            AT_THE_START_LINE = 7,              //
            RUNNING = 8,                        //
            AT_THE_FINNISH_LINE = 9,            //
            WAITING_FOR_A_RACE_TO_START = 10,   //
            APPRAISING_THE_HORSES = 11,         //
            PLACING_A_BET = 12,                 //
            WATCHING_A_RACE = 13,               //
            COLLECTING_THE_GAINS = 14,          //
            CELEBRATING = 15,                   //
            SETTLING_ACCOUNTS = 16;

}
