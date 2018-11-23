package mutual_exclusion;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
//            System.setProperty("java.rmi.server.hostname","145.94.157.201");
        }

        // Start registry
        try {
            LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }

        MessageSender msgSender = new MessageSender();
        int n = 4;
        for (int i = 0; i < n; i++) {
            if (i == 0) {
                Component component = new Component(i + 1, n, new Token(n));
                msgSender.addComponent(component);
            } else {
                Component component = new Component(i + 1, n, null);
                msgSender.addComponent(component);
            }
        }

        msgSender.startSender();
    }
}
