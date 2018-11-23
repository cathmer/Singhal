package mutual_exclusion;

import java.util.ArrayList;
import java.util.HashMap;

public class Component {

    private ArrayList<Integer> requestNumbers;
    private ArrayList<States> processStates;
    private int processId;
    private int numberOfProcesses;
    private Token token;
    private Send sender;
    private HashMap<Integer, String> receiverMap;

    public enum States {
        R, E, H, O
    };

    public Component(int processId, int numberOfProcesses, Token token, HashMap<Integer, String> receiverMap) {
        this.receiverMap = receiverMap;
        this.processId = processId;
        this.numberOfProcesses = numberOfProcesses;
        requestNumbers = new ArrayList<>();
        processStates = new ArrayList<>();

        if (processId == 1) {
            processStates.add(States.H);
            for (int i = 2; i <= numberOfProcesses; i++) {
                processStates.add(States.O);
            }
        } else {
            for (int i = 1; i < processId; i++) {
                processStates.add(States.R);
            }

            for (int i = processId; i <= numberOfProcesses; i++) {
                processStates.add(States.O);
            }
        }

        for (int i = 1; i <= numberOfProcesses; i++) {
            requestNumbers.add(0);
        }

        sender = new Send(this);
        StartRegistry startReg = new StartRegistry();
        startReg.startRegistry("Send" + processId, sender);

        this.token = token;
    }

    public boolean hasToken() {
        return token != null;
    }

    public void sendRequest() {
        System.out.println("Process " + processId + " requesting access to CS");

        processStates.set(processId - 1, States.R);
        requestNumbers.set(processId - 1, requestNumbers.get(processId - 1) + 1);
        if (hasToken()) {
            receiveToken(token);
            return;
        }

        for (int i = 0; i < processStates.size(); i++) {
            if (i != processId - 1 && processStates.get(i) == States.R) {
                String registryName = "Send" + (i + 1);
                Request request = new Request(processId, i + 1, requestNumbers.get(processId - 1));
                sender.sendRequest(registryName, receiverMap.get(i + 1), request);
            }
        }

//        System.out.println("State in process " + processId + " after request to CS: " + processStates.get(processId - 1));
//        System.out.println("Request number in process " + processId + " after request to CS: " + requestNumbers.get(processId - 1));
    }

    public void receiveRequest(Request request) {
//        System.out.println("Received request in process " + processId + ": " + request);
//        System.out.println("States in process " + processId + " before receiving: " + processStates);

        int fromProcessId = request.getFromProcessId();
        requestNumbers.set(fromProcessId - 1, request.getRequestNumber());
        States localState = processStates.get(processId - 1);
        switch(localState) {
            case E:
                processStates.set(fromProcessId - 1, States.R);
                break;
            case O:
                processStates.set(fromProcessId - 1, States.R);
                break;
            case R:
                if (processStates.get(fromProcessId - 1) != States.R) {
                    processStates.set(fromProcessId - 1, States.R);
                    sender.sendRequest(new Request(processId, fromProcessId, requestNumbers.get(processId - 1)));
                }
                break;
            case H:
                processStates.set(fromProcessId - 1, States.R);
                processStates.set(processId - 1, States.O);
                token.updateProcessState(fromProcessId, States.R);
                token.updateRequestNumber(fromProcessId, request.getRequestNumber());
                String registryName = "Send" + fromProcessId;
                sender.sendToken(registryName, receiverMap.get(fromProcessId), token);
                token = null;
                break;
        }

//        System.out.println("States in process " + processId + " after receiving: " + processStates);
    }

    public void receiveToken(Token token) {
        this.token = token;
        processStates.set(processId - 1, States.E);
        new CriticalSection().executeCS(processId);
        processStates.set(processId - 1, States.O);
        token.updateProcessState(processId, States.O);
//        System.out.println("State in process " + processId + " after executing CS: " + processStates.get(processId - 1));
//        System.out.println("Request number in process " + processId + " after executing CS: " + requestNumbers.get(processId - 1));

        boolean nobodyRequesting = true;
        int requester = -1;

        for (int i = 0; i < numberOfProcesses; i++) {
            if (requestNumbers.get(i) > token.getRequestNumber(i + 1)) {
                token.updateRequestNumber(i + 1, requestNumbers.get(i));
                token.updateProcessState(i + 1, processStates.get(i));
            } else {
                requestNumbers.set(i, token.getRequestNumber(i + 1));
                processStates.set(i, token.getState(i + 1));
            }

            if (nobodyRequesting && processStates.get(i) == States.R) {
                nobodyRequesting = false;
                requester = i  + 1;
            }
        }

//        System.out.println("States in process " + processId + " after updating: " + processStates);
//        System.out.println("Request numbers in process " + processId + " after updating: " + requestNumbers);

        if (nobodyRequesting) {
            System.out.println("Nobody requesting, so process " + processId + " keeps the token");
            processStates.set(processId - 1, States.H);
        } else {
            String registryName = "Send" + requester;
            sender.sendToken(registryName, receiverMap.get(requester), token);
            this.token = null;
        }
    }
}
