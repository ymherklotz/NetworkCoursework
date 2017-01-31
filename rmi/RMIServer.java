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
		// TODO: On receipt of first message, initialise the receive buffer
		if(msg.messageNum == 1)
			receivedMessages = new int[msg.totalMessages];

		// this may be a better implementations ????
		// if(receivedMessages == null)
		//  receivedMessages = new int[msg.totalMessages];

		// TODO: Log receipt of the message
		System.out.println("Receieved Message: " + Integer.toString(msg.messageNum) + " out of " + Integer.toString(msg.totalMessages));

		// TODO: If this is the last expected message, then identify
		//        any missing messages
	}


	public static void main(String[] args) {
		RMIServerI rmis = null;

		// TODO: Initialise Security Manager
		if(System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());

		// TODO: Instantiate the server class

		// TODO: Bind to RMI registry
		try {
			rmis = new RMIServer();

			// Binding server
			rebindServer("//127.0.0.1:1099/RMIServer", rmis);

			System.out.println("RMIServer ready");
		} catch(Exception e) {
			System.err.println("RMIServer exception:");
			e.printStackTrace();
		}
	}

	protected static void rebindServer(String serverURL, RMIServerI server) {
		// TODO:
		// Start / find the registry (hint use LocateRegistry.createRegistry(...)
		// If we *know* the registry is running we could skip this (eg run rmiregistry in the start script)

		// TODO:
		// Now rebind the server to the registry (rebind replaces any existing servers bound to the serverURL)
		// Note - Registry.rebind (as returned by createRegistry / getRegistry) does something similar but
		// expects different things from the URL field.
		try {
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

			Naming.rebind(serverURL, server);

			System.out.println("RMIServer bound");
		} catch(Exception e) {
			System.err.println("RMIRebindServer exception:");
			e.printStackTrace();
		}
	}
}
