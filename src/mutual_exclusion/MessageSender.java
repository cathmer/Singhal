package mutual_exclusion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MessageSender {

//    private Component component;
    private HashMap<Integer, Component> components;

    public MessageSender() {
        components = new HashMap<>();
    }

    public void addComponent(int processId, Component component) {
        components.put(processId, component);
    }

    /**
     * Starts a scanner that takes input, which is used to send message.
     */
    public void startSender() {
        Scanner scan = new Scanner(System.in);
        try {
            while (scan.hasNextLine()) {
                int requestingProcess = scan.nextInt();
                components.get(requestingProcess ).sendRequest();
            }
        } finally {
            scan.close();
        }
    }
}
