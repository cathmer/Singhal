package mutual_exclusion;

import java.io.Serializable;
import java.util.ArrayList;

public class Token implements Serializable {

    private static final long serialVersionUID = 7526471155622776148L;
    private ArrayList<Integer> requestNumbers;
    private ArrayList<Component.States> processStates;

    public Token(int numberOfProcesses) {
        requestNumbers = new ArrayList<>();
        processStates = new ArrayList<>();
        for (int i = 0; i < numberOfProcesses; i++) {
            requestNumbers.add(0);
            processStates.add(Component.States.O);
        }
    }

    public void updateRequestNumber(int processId, int reqNumber) {
        requestNumbers.set(processId - 1, reqNumber);
    }

    public void updateProcessState(int processId, Component.States state) {
        processStates.set(processId - 1, state);
    }

    public int getRequestNumber(int processId) {
        return requestNumbers.get(processId - 1);
    }

    public Component.States getState(int processId) {
        return processStates.get(processId - 1);
    }

    @Override
    public String toString() {
        return "Token: \nRequest numbers: " + requestNumbers + "\nProcessStates: " + processStates;
    }
}
