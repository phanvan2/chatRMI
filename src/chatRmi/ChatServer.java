package chatRmi;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.*;

import Class.Constant;



public class ChatServer {
public static void main (String[] argv) {
    try {
    			 
            Scanner s=new Scanner(System.in);


            Chat server = new Chat("server");   
          
   
            //Naming.rebind("rmi://localhost/ABC", server);
            Registry registry =  LocateRegistry.createRegistry(new Constant().PORT);
   		 
 		   registry.rebind(ChatInterface.class.getSimpleName(), server);




//            while(true){
//                String msg=s.nextLine().trim();
//                if (server.getClient()!=null){
//                	for (ChatInterface s1 : server.getClients()){
//                		
//                         String msg1="["+server.getName()+"] "+msg;
//                         s1.send(msg1);
//                        
//                	}
//                	
////                    ChatInterface client=server.getClient();
////                    msg="["+server.getName()+"] "+msg;
////                    client.send(msg);
//                }
//               
//            }

        }catch (Exception e) {
            System.out.println("[System] Server failed: " + e);
        }
    }
}