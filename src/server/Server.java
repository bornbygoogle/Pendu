package server;

import java.io.IOException;
import java.util.HashMap;

public class Server {

	private int port;

	private Connection conn;

	private HashMap<ConnectedClient, Thread> clients;
	
	public Server(MainServer main, int _port) throws IOException 
	{
		this.port = _port;
		this.clients = new HashMap<ConnectedClient, Thread>();
		System.out.println("Connect to server port " + this.port);

		conn = new Connection(main, this);
		Thread threadConnection = new Thread(conn);
		threadConnection.start();
	}
	
	public int getPort() {
		return this.port;
	}
	
	public HashMap<ConnectedClient, Thread> getClients() {
		return this.clients;
	}
	
	public void addClient(ConnectedClient client) {
		Thread thread = new Thread(client);
		thread.start();
		this.clients.put(client, thread);
	}

	public void stopServerRunning()
	{
		if (!(clients.isEmpty()))
		{
			for(ConnectedClient client : this.clients.keySet())
				client.closeClient();
			this.clients.clear();
		}
		this.conn.arret();
	}

	public void disconnectedClient(ConnectedClient discClient) {
		discClient.closeClient();
		this.clients.remove(discClient);
	}
}
