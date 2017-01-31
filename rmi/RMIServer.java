/*
 * Created on 01-Mar-2016
 */
package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

import common.*;

public class RMIServer extends UnicastRemoteObject implements RMIServerI {
	public static final long serialVersionUID = 52L;

	private int totalMessages = -1;
	private int[] receivedMessages;

	public RMIServer() throws RemoteException {
		super();
	}

	public void receiveMessage(MessageInfo msg) throws RemoteException {
		// initialise the buffer when first message is received
		if(msg.messageNum == 0) {
			totalMessages = 0;
			receivedMessages = new int[msg.totalMessages];
		}

		// increment the total messages when a new call is made
		totalMessages++;

		System.out.println("Receieved Message: " + Integer.toString(msg.messageNum + 1) + " out of " + Integer.toString(msg.totalMessages));

		// buffer the incomming messages
		receivedMessages[totalMessages - 1] = msg.messageNum;

		// when last expected message was sent, see which ones were lost
		if(msg.messageNum == msg.totalMessages - 1) {
			// for(int i = 0; i < totalMessages; ++i)
			//  System.out.println("Receieved Message: " + Integer.toString(receivedMessages[i] + 1) + " out of " + Integer.toString(msg.totalMessages));
			System.out.println("#######################################");
			System.out.println("Messages received: " + Integer.toString(totalMessages));
			System.out.println("Total messages sent: " + Integer.toString(msg.totalMessages));
			System.out.println("Success rate: " + Double.toString((double)totalMessages / (double)msg.totalMessages * 100.0) + "%");
			totalMessages = -1;
		}
	}


	public static void main(String[] args) {
		RMIServer rmis = null;

		try {
			// initialise rmiserver object
			rmis = new RMIServer();

			// binding server to correct ip
			rebindServer("//129.31.219.236/RMIServer", rmis);

			System.out.println("RMIServer ready");
		} catch(Exception e) {
			System.err.println("RMIServer exception:");
			e.printStackTrace();
		}
	}

	protected static void rebindServer(String serverURL, RMIServer server) {
		try {
			// create registry at specific registry port
			LocateRegistry.createRegistry(1099);

			// rebinding server to the server url
			Naming.rebind(serverURL, server);

			System.out.println("RMIServer bound");
		} catch(Exception e) {
			System.err.println("RMIRebindServer exception:");
			e.printStackTrace();
		}
	}
}
