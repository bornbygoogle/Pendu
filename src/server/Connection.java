package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection implements Runnable {

	private MainServer main;
	
	private Server server;
	private ServerSocket serverSocket;

    protected volatile boolean running = true;
	
	public Connection(MainServer main, Server server) {
		this.main = main;
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
		System.out.println("Ecoute réseau activée");
		while(this.running) {
			try {
				// On accepte les connexions entrantes
				Socket sockNewClient = this.serverSocket.accept();
				// On ajoute la connexion dans la liste des nos clients connectés
				ConnectedClient newClient = new ConnectedClient(sockNewClient, this.main);
				this.server.addClient(newClient);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Ecoute réseau désactivée");
	}
}
