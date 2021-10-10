package chatRmi;
import java.rmi.*;

import java.rmi.server.*;
import java.util.Vector;

public class Chat extends UnicastRemoteObject implements ChatInterface  {

    public String name;
    public String s = "" ; 
    public ChatInterface client=null;
    public Vector<ChatInterface> clients = new Vector<ChatInterface>(); 
    public Chat(String n)  throws RemoteException { 
        this.name=n;   
    }
    public String getName() throws RemoteException {
    	
        return this.name;
    }

    public void setClient(ChatInterface c){
        client=c;
        clients.add(c); 
    }
    public String receive() {
    	String s1 = s ; 
    	s = "" ;
    	if( s1 == "") {
    		return null;
    	}else {
    		System.out.println(name);
        	return s1; 

    	}
    }
    public ChatInterface getClient(){
        return client;
    }
    
    public Vector<ChatInterface> getClients(){
    	return this.clients; 
    }

    public void send(String s) throws RemoteException{
      //  System.out.println(s);
    //	if(!s.contains(name)) {
            this.s = s ;
    //	}
 
    }   
}