package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Server {

	private int port;

	private Connection conn;

	private List<ConnectedClient> clients;
	
	public Server(MainServer main, int _port) throws IOException 
	{
		this.port = _port;
		this.clients = new ArrayList<ConnectedClient>();
		System.out.println("Connect to server port " + port);

		conn = new Connection(main, this);
		Thread threadConnection = new Thread(conn);
		threadConnection.start();
	}
	
	public int getPort() {
		return this.port;
	}
	
	public List<ConnectedClient> getClients() {
		return this.clients;
	}
	
	public void addClient(ConnectedClient client) {
		this.clients.add(client);
	}

	public void stopServerRunning()
	{
		if (!(clients.isEmpty()))
		{
			for(ConnectedClient client : this.clients)
				disconnectedClient(client);
			this.clients.clear();
		}
		this.conn.arret();
	}

	public void disconnectedClient(ConnectedClient discClient) {
		discClient.closeClient();
	}
}
