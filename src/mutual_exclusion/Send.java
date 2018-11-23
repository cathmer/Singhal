package mutual_exclusion;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.TimeUnit;

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

    /**
     * This method can be invoked by another process to send a request to this process. This method starts a new thread.
     * @param request : the request to send.
     */
//    public void sendMessage(Request request, int delay) {
//        new Thread(() -> {
//            try {
//                TimeUnit.SECONDS.sleep(delay);
//                System.out.println(request);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }

    public void sendRequest(Request request) {
        // Start new thread here
        new Thread(() -> {
            try {
//                TimeUnit.SECONDS.sleep(delay);
//                System.out.println(request);
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
//                TimeUnit.SECONDS.sleep(delay);
//                System.out.println(request);
                component.receiveToken(token);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Method to send a request to a different process.
     * @param registryName : registry name of the other process.
     * @param receiver : the name of the ip of the receiver.
     * @param request : the request to be sent.
     * @param delay : the delay.
     */
//    public void sendMessage(String registryName, String receiver, Request request) {
//        // Sends a request using the remote method of another host
//        try {
//            String name = registryName;
//            Registry remoteReg = LocateRegistry.getRegistry(receiver);
//            Send_RMI send = (Send_RMI) remoteReg.lookup(name);
//            send.sendMessage(request, delay);
//        } catch (Exception e) {
//            System.err.println("Sending request exception:");
//            e.printStackTrace();
//        }
//    }

    public void sendToken(String registryName, Token token) {
        // Sends a request using the remote method of another host
        try {
            String name = registryName;
            String receiver = "localhost";
            Registry remoteReg = LocateRegistry.getRegistry(receiver);
            Send_RMI send = (Send_RMI) remoteReg.lookup(name);
            System.out.println("Sent token " + token + " to " + registryName.substring(registryName.length() - 1));
            send.sendToken(token);
        } catch (Exception e) {
            System.err.println("Sending token exception:");
            e.printStackTrace();
        }
    }

    public void sendRequest(String registryName, Request request) {
        // Sends a request using the remote method of another host
        try {
            String name = registryName;
            String receiver = "localhost";
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
