package algorithm;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Dictionary;

/**
 * Created by installation on 2/28/2019.
 */
public interface CollaborativeFilteringInterface extends Remote {
    public  double PearsonCorrelation(Dictionary userQuestion1, Dictionary userAnswer1, String userOne, String userTwo)throws RemoteException;
}
