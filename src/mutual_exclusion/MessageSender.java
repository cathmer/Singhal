package mutual_exclusion;

import java.util.ArrayList;
import java.util.Scanner;

public class MessageSender {

//    private Component component;
    private ArrayList<Component> components;

    public MessageSender() {
        components = new ArrayList<>();
    }

    public void addComponent(Component component) {
        components.add(component);
    }

    /**
     * Starts a scanner that takes input, which is used to send message.
     */
    public void startSender() {
        Scanner scan = new Scanner(System.in);
        try {
            while (scan.hasNextLine()) {
                int requestingProcess = scan.nextInt();
                components.get(requestingProcess - 1).sendRequest();
            }
        } finally {
            scan.close();
        }
    }
}
