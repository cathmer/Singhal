package mutual_exclusion;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * This class implements the Send_RMI interface, which allows for the sendMessage method to be invoked by another
 * process.
 */
public class Send implements Send_RMI {

    private Component component;

    /**
     * Constructs an object with the component of the receiving process.
     * @param component : the component that receives a token/request.
     */
    public Send(Component component) {
        this.component = component;
    }

    public void sendRequest(Request request) {
        // Start new thread here
        new Thread(() -> {
            try {
                component.receiveRequest(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendToken(Token token) {
        // Start new thread here
        new Thread(() -> {
            try {
                component.receiveToken(token);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendToken(String registryName, String receiver, Token token) {
        // Sends a request using the remote method of another host
        try {
            String name = registryName;
//            String receiver = "localhost";
            Registry remoteReg = LocateRegistry.getRegistry(receiver);
            Send_RMI send = (Send_RMI) remoteReg.lookup(name);
            System.out.println("Sent token " + token + "\n to process " + registryName.substring(registryName.length() - 1));
            send.sendToken(token);
        } catch (Exception e) {
            System.err.println("Sending token exception:");
            e.printStackTrace();
        }
    }

    public void sendRequest(String registryName, String receiver, Request request) {
        // Sends a request using the remote method of another host
        try {
            String name = registryName;
//            String receiver = "localhost";
            Registry remoteReg = LocateRegistry.getRegistry(receiver);
            Send_RMI send = (Send_RMI) remoteReg.lookup(name);
            System.out.println("Sent request: " + request);
            send.sendRequest(request);
        } catch (Exception e) {
            System.err.println("Sending request exception:");
            e.printStackTrace();
        }
    }
}
