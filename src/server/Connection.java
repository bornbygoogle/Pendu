package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Statement;

public class Connection implements Runnable {

	private Server server;
	private ServerSocket serverSocket;
	
	public Connection(Server server) {
		this.server = server;
		try {
			this.serverSocket = new ServerSocket(server.getPort());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void run() {
		while(true) {
			try {
				Socket sockNewClient = this.serverSocket.accept();
				ConnectedClient newClient = new ConnectedClient(this.server, sockNewClient);
				this.server.addClient(newClient);
				Thread threadNewClient = new Thread(newClient);
				threadNewClient.start();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Statement createStatement() 
	{
		// TODO Auto-generated method stub
		return null;
	}
}
