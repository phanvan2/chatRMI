package chatRmi;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.*;

import Class.Constant;


public class ChatClient {
	ChatInterface server = null ; 
	ChatInterface client ; 
	public ChatClient(String s ) {
		try {
			client = new Chat(s);

     	  
		  	Registry  registry = LocateRegistry.getRegistry(new Constant().HOST, new Constant().PORT);
		  	 server  = (ChatInterface) registry.lookup(ChatInterface.class.getSimpleName());
       
        
		  	server.setClient(client);
		  	
		}catch(Exception e) {
			 System.out.println(e.getMessage());
		}

	}
    public static void main (String[] argv) {
    	Scanner key = new Scanner(System.in);
    	System.out.print("Moi ban nhap ten: " );
    	String name = key.nextLine().trim() ; 
    	ChatClient client = new ChatClient(name) ; 
    	
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					String mess = client.ClientReceive() ; 
					if( mess != null) {
						System.out.println(mess) ; 
					}
				}
			}
		}).start();
    	
    	while(true) {
    		String strMess = key.nextLine() ; 
    		client.sendMess(strMess); 
    	}
    }
    public String ClientReceive() {
    	String s = null ; 
    	try {
			 s =  server.receive() ;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	return s  ; 
    	
    }
    public boolean sendMess(String mess) {
    	try {
        	String msg=""+client.getName()+": " + mess;                  
            server.send(msg);
           
//            for (ChatInterface s12 : client.getClients()){       		
//                 String msg1=""+server.getName()+": "+msg;
//                 s12.send(msg1);
//                
//        	}
//            ChatInterface s12 = client.getClient() ; 
//            String msg1=""+server.getName()+": "+msg;
//            s12.send(msg1);
    	}catch(Exception e ) {
    		System.out.println(e.getMessage());
    	}

        return true ; 
    }

}