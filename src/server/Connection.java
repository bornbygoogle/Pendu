package server;

import java.io.IOException;
import java.net.ServerSocket;

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
		while(running) {
			//this.running = false;
			if (!running)
				System.out.println("Server will off");
			/*try {
				Socket sockNewClient = this.serverSocket.accept();
				ConnectedClient newClient = new ConnectedClient(this.server, sockNewClient);
				this.server.addClient(newClient);
				Thread threadNewClient = new Thread(newClient);
				threadNewClient.start();
				//if ()
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}*/
		}
	}
}
