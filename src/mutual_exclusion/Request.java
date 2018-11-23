package mutual_exclusion;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This class holds all the variables that are sent with a message. Including the actual message (a string), the processId
 * of the process the message is sent to, the processId of the process that sent the message, the buffer sent by the sender
 * and the timestamp sent by the sender.
 */
public class Request implements Serializable {

    private static final long serialVersionUID = 7526471155622776147L;
    private int toProcessId;
    private int fromProcessId;
    private int reqNumber;

    /**
     * Constructs a message.
     *
     * @param toProcessId   : the id of the process to send to.
     * @param fromProcessId : the id of the process that sent the message.
     */
    public Request(int fromProcessId, int toProcessId, int reqNumber) {
        this.toProcessId = toProcessId;
        this.fromProcessId = fromProcessId;
        this.reqNumber = reqNumber;
    }

    public int getFromProcessId() { return fromProcessId; }

    public int getRequestNumber() { return reqNumber; }

    @Override
    public String toString() {
        return "from " + fromProcessId + ", to " + toProcessId + " with request nr " + reqNumber;
    }
}
