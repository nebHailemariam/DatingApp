package algorithm;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Main {

    public static void main(String[] args) throws RemoteException {

        CollaborativeFiltering collaborativeFiltering = new CollaborativeFiltering();
        Registry reg;
        reg = LocateRegistry.createRegistry(3000);
        reg.rebind("calculate", collaborativeFiltering);
        System.out.println("RMI Server is ready");
    }
}
