package server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import main.Main;

public class Server extends Thread {
    private Listener listener;
    private final String id = "SERVER";
    private boolean go;
    private ArrayList<Connection> connections;
    private int counter;

    public Server(int port) {
	Main.printInfo(id, "Attempting to start a new server on port " + port
		+ "...");
	try {
	    listener = new Listener(this, port);
	} catch (IOException e) {
	    Main.printAlert(id, "Error starting server!");
	    e.printStackTrace();
	}
	if (listener.isBound()) {
	    Main.printInfo(id, "Server started successfully!");
	    connections = new ArrayList<Connection>();
	    counter = 0;
	    start();
	} else {
	    Main.printAlert(id,
		    "Unable to verify server startup, shutting down...");
	    listener.shutdown();
	    clearConnections();
	}
    }

    @Override
    public void run() {
	go = true;
	while (go) {
	    interrupted();
	}
	Main.printInfo(id, "Stopping listener...");
	listener.shutdown();
	Main.printInfo(id, "Clearing active connections...");
	clearConnections();
	Main.printInfo(id, "Server shut down successfully!");
    }

    public boolean shutdown() {
	go = false;
	interrupt();
	while (isAlive())
	    interrupted();
	return true;
    }

    public void newConnection(Socket client) {
	Main.printInfo(id, "New connection from ("
		+ client.getInetAddress().getHostAddress().trim() + ")");
	Connection con = new Connection(client, counter);
	connections.add(con);
	counter++;
    }

    public void clearConnections() {
	Iterator<Connection> clearer = connections.iterator();
	Connection c;
	while (clearer.hasNext()) {
	    c = clearer.next();
	    c.dispose();
	    clearer.remove();
	}
    }
}
