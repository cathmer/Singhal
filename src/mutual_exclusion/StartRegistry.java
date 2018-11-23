package mutual_exclusion;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class starts the registry for a process with a registry name and the Send object.
 */
public class StartRegistry {

    /**
     * Method to start the registry.
     * @param registryName : the name of the registry.
     * @param sendMsg : the Send object which can be invoked by a different process.
     */
    public void startRegistry(String registryName, Send sendMsg) {
        // Initiate registry
        try {
            String name = registryName;
            Send_RMI stub =
                    (Send_RMI) UnicastRemoteObject.exportObject(sendMsg, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("Send bound to " + registryName);
        } catch (Exception e) {
            System.err.println("Send exception:");
            e.printStackTrace();
        }
    }
}
