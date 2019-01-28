package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection implements Runnable {

	private Server server;
	private ServerSocket serverSocket;

    protected volatile boolean running = true;
	
	public Connection(Server server) {
		this.server = server;
		try {
			this.serverSocket = new ServerSocket(server.getPort());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void arret() {
		this.running = false;
	}

	@Override
	public synchronized void run() {
		System.out.println("Server run");
		Socket sockNewClient;
		while(running) {
			//this.running = false;

			try {
				if (!running)
				{
					System.out.println(this.serverSocket.getLocalPort());
					System.out.println(this.serverSocket.getLocalSocketAddress());
					System.out.println("Server will off");
				}
				sockNewClient = this.serverSocket.accept();
				System.out.println("Addresse : " + sockNewClient.getLocalAddress() + " port : " + sockNewClient.getPort());
				/*ConnectedClient newClient = new ConnectedClient(this.server, sockNewClient);
				this.server.addClient(newClient);
				Thread threadNewClient = new Thread(newClient);
				threadNewClient.start();
				//if ()*/
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
