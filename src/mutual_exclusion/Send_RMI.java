package mutual_exclusion;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Send_RMI extends Remote {

    void sendRequest(Request request) throws RemoteException;

    void sendToken(Token token) throws RemoteException;
}
