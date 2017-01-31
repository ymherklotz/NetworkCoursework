/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.MessageInfo;

public class RMIClient {
	public static void main(String[] args) {
		RMIServerI iRMIServer = null;

		// Check arguments for Server host and number of messages
		if(args.length < 2) {
			System.out.println("Needs 2 arguments: ServerHostName/IPAddress, TotalMessageCount");
			System.exit(-1);
		}

		String urlServer = new String("rmi://" + args[0] + "/RMIServer");
		int numMessages = Integer.parseInt(args[1]);

		// TODO: Initialise Security Manager
		if(System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());

		// TODO: Bind to RMIServer
		try {
			String name = "RMIServer";
			Registry registry = LocateRegistry.getRegistry();
			iRMIServer = (RMIServerI)registry.lookup(name);
		} catch(Exception e) {
			System.err.println("RMIClient exception:");
			e.printStackTrace();
		}

		// TODO: Attempt to send messages the specified number of times
		for(int i = 1; i < numMessages + 1; i++) {
			MessageInfo msg = new MessageInfo(numMessages, i);
			try {
				iRMIServer.receiveMessage(msg);
				System.out.println("Successfully sent msg: " + Integer.toString(i));
			} catch(Exception e) {
				System.err.println("RMIClient exception:");
				e.printStackTrace();
			}
		}
	}
}
