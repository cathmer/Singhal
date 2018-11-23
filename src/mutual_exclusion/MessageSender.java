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
//            int msgCounter = 0;
            while (scan.hasNextLine()) {
//                msgCounter++;
//                int fromProcess = scan.nextInt();
//
//                ArrayList<Integer> toProcesses = new ArrayList<>();
//                String next = scan.next();
//                while(isInteger(next, 10)) {
//                    toProcesses.add(Integer.parseInt(next));
//                    next = scan.next();
//                }
//
//                String receiver = next;
//                if (receiver.equals("l")) receiver = "localhost";
//
//                int delay = scan.nextInt();

                int requestingProcess = scan.nextInt();
                components.get(requestingProcess - 1).sendRequest();
            }
        } finally {
            scan.close();
        }
    }

//    private boolean isInteger(String s, int radix) {
//        if(s.isEmpty()) return false;
//        for(int i = 0; i < s.length(); i++) {
//            if(i == 0 && s.charAt(i) == '-') {
//                if(s.length() == 1) return false;
//                else continue;
//            }
//            if(Character.digit(s.charAt(i),radix) < 0) return false;
//        }
//        return true;
//    }
}
