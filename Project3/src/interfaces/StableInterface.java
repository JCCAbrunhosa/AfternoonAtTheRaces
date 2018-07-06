package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface stable
 */

public interface StableInterface extends Remote {
    /**
     * Summon all horses to paddock
     */
    void summonHorsesToPaddock() throws RemoteException;

    /**
     * Proceed to the stable
     *
     * @param horseId Horse ID
     */
    void proceedToStable(int horseId) throws RemoteException;

    /**
     * Método para "fechar" a classe - Apenas toma valor na classe remota.
     */
    void close() throws RemoteException ;
}
