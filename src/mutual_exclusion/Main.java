package mutual_exclusion;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        String hostName = "145.94.159.69";

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
            System.setProperty("java.rmi.server.hostname",hostName);
        }

        // Start registry
        try {
            LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }

        HashMap<Integer, String> hostMap = new Reader().readConfiguration();
        MessageSender msgSender = new MessageSender();
        int n = hostMap.size();
        for (Integer i : hostMap.keySet()) {
            if (hostMap.get(i).equals(hostName)) {
                Token token = null;
                if (i == 1) {
                    token = new Token(hostMap.size());
                }
                Component component = new Component(i, hostMap.size(), token, hostMap);
                msgSender.addComponent(i, component);
            }
        }

//        for (int i = 0; i < n; i++) {
//            if (i == 0) {
//                Component component = new Component(i + 1, n, new Token(n), hostMap);
//                msgSender.addComponent(component);
//            } else {
//                Component component = new Component(i + 1, n, null, hostMap);
//                msgSender.addComponent(component);
//            }
//        }

        msgSender.startSender();

//        1 145.94.159.69
//        2 145.94.191.135
    }
}
