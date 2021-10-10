package chatRmi;
import java.rmi.*;
import java.util.Vector;

public interface ChatInterface extends Remote {
    public String getName() throws RemoteException;
    public void send(String msg) throws RemoteException;
    public String receive() throws RemoteException;
    public void setClient(ChatInterface c)throws RemoteException;
    public ChatInterface getClient() throws RemoteException;
    public Vector<ChatInterface> getClients() throws RemoteException; 
}